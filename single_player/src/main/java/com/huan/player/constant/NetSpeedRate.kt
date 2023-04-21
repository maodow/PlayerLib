package com.huan.player.constant

import android.net.TrafficStats
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

/**
 * 获取下载速度
 */
object NetSpeedRate {
    private val TAG = NetSpeedRate::class.java.simpleName
    private var lastTotalRxBytes: Long = 0
    private var lastTimeStamp: Long = 0
    private var uidFileDir: File? = null
    private var brReceived: RandomAccessFile? = null
    private var brSent: RandomAccessFile? = null

    /**
     * 单位是 kb/s
     */
    fun getNetSpeed(uid: Int): Long {
        val nowTotalRxBytes = getBytes(uid)
        val nowTimeStamp = System.currentTimeMillis()
        val divide = nowTimeStamp - lastTimeStamp
        if (divide == 0L) {
            lastTimeStamp = nowTimeStamp
            lastTotalRxBytes = nowTotalRxBytes
            return 0
        }
        val speed =
            (nowTotalRxBytes - lastTotalRxBytes) * 1000 / divide //毫秒转换
        lastTimeStamp = nowTimeStamp
        lastTotalRxBytes = nowTotalRxBytes
        return speed
    }

    /**
     * getApplicationInfo().uid
     * 所以包括 TCP 和 UDP 包。Rx 接收，Tx 发送，bytes 字节数，
     *TrafficStats.getUidRxBytes(uid)  等于-1 说明这个设备不支持
     */
    private fun getBytes(uid: Int): Long {
        return if (TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED.toLong()) getTotalRxBytes(
            uid
        ) / 1024 else TrafficStats.getTotalRxBytes() / 1024 //转为KB
    }

    /**
     * 通过uid查询文件夹中的数据
     * @param localUid
     * @return
     */
    private fun getTotalRxBytes(uid: Int): Long {
        var textReceived: String? = "0"
        try {
            var receivedLine: String? = ""
            getBufferedReader(uid)
            brReceived?.seek(0)
            brSent?.seek(0)
            if (brReceived?.readLine().also({ receivedLine = it }) != null) {
                textReceived = receivedLine
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return textReceived!!.toLong()
    }

    private fun getBufferedReader(uid: Int): RandomAccessFile? {
        if (uidFileDir == null) {
            uidFileDir = File("/proc/uid_stat/$uid")
            val uidActualFileReceived = File(uidFileDir, "tcp_rcv") //接受
            brReceived = RandomAccessFile(uidActualFileReceived, "r")
            //  val uidActualFileSent = File(uidFileDir, "tcp_snd")  //发送
            // brSent = RandomAccessFile(uidActualFileSent,"r")
        }
        return brReceived
    }

    /**
     * 转换文件大小
     */
    fun FormetFileSize(byte: Long): String? {
        var fileSizeString = "0KB"
        fileSizeString = if (byte < 1024) {
            "${byte}B"
        } else if (byte < 1048576) {
            "${byte / 1024}KB"
        } else if (byte < 1073741824) {
            "${byte / 1048576}MB"
        } else {
            "${byte / 1073741824}G"
        }
        return fileSizeString
    }

    fun onClose() {
        brReceived?.close()
    }
}
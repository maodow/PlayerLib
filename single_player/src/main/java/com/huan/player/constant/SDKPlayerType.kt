package tv.huan.hwplayer.constant

enum class SDKPlayerType {
    //使用android原生的播放器 MediaPlayer 原生播放器问题多,不建议使用
    SDK_TYPE_ANDROID,
    SDK_TYPE_EXO,     //使用EXO 播放器
    SDK_TYPE_IJK,     //使用ijkplayer 播放器
    VIEW_TYPE_TEXTURE, // 展示播放器视图的View 使用 TextureView
    VIEW_TYPE_SURFACE  // 展示播放器视图的View  使用 SurfaceView
}
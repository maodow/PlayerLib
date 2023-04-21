package com.huan.player.ui.preview;

import android.text.TextUtils;
import android.util.Log;

import com.huan.player.constant.Logcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
/**
 * description:
 *
 * @author jeremyyang
 * @date 2020/1/3
 */
public class PreviewUtils {
    private static String TAG = "HWPlayer";

    /**
     * xml解析后的节点个数
     */
    private final static int DEFINITION_COUNT = 7;

    /**
     * 以下参数定义键prviewdata
     */
    private final static String PD = "pd";
    private final static String C = "c";
    private final static String CD = "cd";
    private final static String FN = "fn";
    private final static String H = "h";
    private final static String R = "r";
    private final static String W = "w";
    private final static String URL = "url";
    private static final String PIC_URL_SUFFIX = ".jpg/0";

    /**
     * 根据xml解析规则，保存到PreviewData结构体中
     *
     * @param previewPLString
     */
    private static PreviewData buildPreviewDataFromXML(String previewPLString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        PreviewData previewData = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(previewPLString)));
            //节点定义见PreviewData
            NodeList pdNodes = doc.getElementsByTagName(PD);
            if (pdNodes != null && pdNodes.getLength() > 0) {
                int count = 0;
                if (DEFINITION_COUNT > pdNodes.getLength() - 1) {
                    count = pdNodes.getLength() - 1;
                } else if (DEFINITION_COUNT > 0) {
                    count = DEFINITION_COUNT;
                }

                NodeList pdChilds = pdNodes.item(count).getChildNodes();
                previewData = new PreviewData();
                if (pdChilds != null && pdChilds.getLength() > 0) {
                    int length = pdChilds.getLength();
                    String content;
                    for (int i = 0; i < length; i++) {
                        Node pdChild = pdChilds.item(i);
                        if (pdChild.getNodeName() == null) {
                            Logcat.INSTANCE.iTag( "pdChild.getNodeName() == null");
                            return null;
                        }
                        if (pdChild.getNodeName().equalsIgnoreCase(C)) {
                            content = pdChild.getTextContent();
                            try {
                                previewData.column = Integer.parseInt(content);
                            } catch (NumberFormatException e) {
                                Logcat.INSTANCE.iTag( "c NumberFormatException");
                                break;
                            }
                        } else if (pdChild.getNodeName().equalsIgnoreCase(CD)) {
                            content = pdChild.getTextContent();
                            try {
                                previewData.cd = Integer.parseInt(content);
                            } catch (NumberFormatException e) {
                                Logcat.INSTANCE.iTag( "cd NumberFormatException");
                                break;
                            }
                        } else if (pdChild.getNodeName().equalsIgnoreCase(FN)) {
                            content = pdChild.getTextContent();
                            if (!TextUtils.isEmpty(content)) {
                                previewData.fileName = content;
                            } else {
                                Logcat.INSTANCE.iTag("fn NumberFormatException");
                                break;
                            }
                        } else if (pdChild.getNodeName().equalsIgnoreCase(H)) {
                            content = pdChild.getTextContent();
                            try {
                                previewData.height = Integer.parseInt(content);
                            } catch (NumberFormatException e) {
                                Logcat.INSTANCE.iTag( "h NumberFormatException");
                                break;
                            }
                        } else if (pdChild.getNodeName().equalsIgnoreCase(R)) {
                            content = pdChild.getTextContent();
                            try {
                                previewData.row = Integer.parseInt(content);
                            } catch (NumberFormatException e) {
                                Logcat.INSTANCE.iTag( "r NumberFormatException");
                                break;
                            }
                        } else if (pdChild.getNodeName().equalsIgnoreCase(URL)) {
                            content = pdChild.getTextContent();
                            if (!TextUtils.isEmpty(content)) {
                                previewData.url = content;
                            } else {
                                Logcat.INSTANCE.iTag("url Exception: " + content);
                                break;
                            }
                        } else if (pdChild.getNodeName().equalsIgnoreCase(W)) {
                            content = pdChild.getTextContent();
                            try {
                                previewData.width = Integer.parseInt(content);
                            } catch (NumberFormatException e) {
                                Logcat.INSTANCE.iTag( "W NumberFormatException");
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logcat.INSTANCE.eTag( "Exception = " + e.getMessage());
        }

        if (previewData == null || !previewData.isValid()) {
            return null;
        }
        Logcat.INSTANCE.iTag( "column = " + previewData.column + ",row = " + previewData.row + ",height = " + previewData.height + "," +
                "cd = " + previewData.cd + ",fileName = " + previewData.fileName + ",url = " + previewData.url);
        return previewData;
    }

    /**
     * 根据json解析规则，保存到PreviewData结构体中
     *
     * @param previewPLString
     * @return
     */
    private static PreviewData buildPreviewDataFromJSON(String previewPLString) {
        PreviewData previewData = null;
        if (TextUtils.isEmpty(previewPLString)) {
            Logcat.INSTANCE.iTag( "buildPreviewDataFromJSON previewPLString = NULL");
            return null;
        }
        JSONArray bodyArray = null;
        try {
            bodyArray = new JSONArray(previewPLString);
            int bodyLength = bodyArray.length();
            if (bodyLength == 0) {
                Logcat.INSTANCE.iTag( "buildPreviewDataFromJSON bodyLength == 0");
                return null;
            }
            JSONObject body = (JSONObject) bodyArray.get(0);
            if (!body.has(PD)) {
                Logcat.INSTANCE.iTag( "buildPreviewDataFromJSON pd = NULL");
                return null;
            }
            Log.i(TAG, "body != null");
            JSONArray data = body.getJSONArray(PD);

            if (data == null) {
                Logcat.INSTANCE.iTag("buildPreviewDataFromJSON pd = NULL");
                return null;
            }
            int groupDataLength = data.length();
            Logcat.INSTANCE.iTag("groupDataLength = " + groupDataLength);

            if (groupDataLength > 0) {
                previewData = new PreviewData();
                JSONObject groupItem = (JSONObject) data.get(groupDataLength - 1);
                previewData.cd = groupItem.getInt(CD);
                previewData.column = groupItem.getInt(C);
                previewData.fileName = groupItem.getString(FN);
                previewData.row = groupItem.getInt(R);
                previewData.height = groupItem.getInt(H);
                previewData.width = groupItem.getInt(W);
                previewData.url = groupItem.getString(URL);
            }
        } catch (JSONException e) {
            Logcat.INSTANCE.eTag( "buildPreviewDataFromJSON JSONException");
        }

        if (previewData == null) {
            Logcat.INSTANCE.iTag( "buildPreviewDataFromJSON previewData == null");
            return null;
        }
        Logcat.INSTANCE.iTag( "buildPreviewDataFromJSON column = " + previewData.column + ",row = " + previewData.row + ",height = " +
                previewData.height + "," + "cd = " + previewData.cd + ",fileName = " + previewData.fileName + ",url = " + previewData.url);
        return previewData;
    }

    /**
     * 解析缩略图协议数据，默认取的是最后一个<pd></pd>的格式
     * 一般最后一种格式占的资源最大，第三方可以选择合适的。
     */
    public static PreviewData getPreviewData(String plString, int plType) {
        Logcat.INSTANCE.iTag( "plString == " + plString);
        if (TextUtils.isEmpty(plString)) return null;
        PreviewData previewData = null;
        if (plType == 2) {//KTTV_NetVideoInfo.TYPE_XML
            previewData = buildPreviewDataFromXML(plString);
        } else if (plType == 1) {//KTTV_NetVideoInfo.TYPE_JSON
            Logcat.INSTANCE.iTag("videoInfo.getmPLType() == TYPE_JSON");
            previewData = buildPreviewDataFromJSON(plString);
        } else {
            Logcat.INSTANCE.iTag( "videoInfo.getmPLType() != TYPE_XML & TYPE_JSON");
        }
        if (previewData != null) {
            Logcat.INSTANCE.iTag( "TYPE_XML previewData != null");
        } else {
            Logcat.INSTANCE.iTag( "TYPE_XML previewData == null");
        }
        return previewData;
    }
}

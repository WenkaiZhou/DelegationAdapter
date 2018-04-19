package com.kevin.delegationadapter.sample.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * LocalFileUtil
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-07 19:39:52
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class LocalFileUtil {

    /**
     * 获取Asset下文本内容
     * @param context
     * @param str
     * @return
     */
    public final static String getStringFormAsset(Context context, String str) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = in.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @description 从Assets中读取图片
     *
     * @param context
     * @param fileName
     * @return 图片
     * @date 2015-6-11 15:00:55
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return image;
        } finally {
            if(is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取Raw下文本内容
     * @param context
     * @param rawId
     * @return
     */
    public final static String getStringFormRaw(Context context, int rawId) {
        ByteArrayOutputStream baos = null;
        InputStream in = context.getResources().openRawResource(rawId);
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                    baos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

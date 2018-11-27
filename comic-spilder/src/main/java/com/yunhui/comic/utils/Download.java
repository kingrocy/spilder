package com.yunhui.comic.utils;

import com.yunhui.comic.common.BaseConstant;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 15:23
 */
public class Download {

    public static String getFileName(String url) {
        String[] strings = url.split("/");
        String last = strings[strings.length - 1];
        return last.split("_")[0] + "." + last.split("\\.")[1];
    }

    public static void downloadPicture(String imgUrl, Integer index) {
        URL url;
        try {
            url = new URL(imgUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(getFile(imgUrl, index));

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getFile(String imgUrl, Integer index) {

        File file = new File(BaseConstant.IMG_DOWNLOAD_PATH + index + "/" + getFileName(imgUrl));

        if(!file.isDirectory()){
            file.getParentFile().mkdirs();
        }
        return file;

    }
}


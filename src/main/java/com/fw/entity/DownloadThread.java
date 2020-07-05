package com.fw.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 内部类用于实现下载
 *
 * @author yqf
 */
public class DownloadThread implements Runnable {

    /**
     * 下载类
     */
    private Download downLoad;

    /**
     * 多线程读取字节计数变量
     */
    private int count = 0;
    /**
     * 下载起始位置
     */
    private Long startIndex;
    /**
     * 下载结束位置
     */
    private Long endIndex;

    public DownloadThread(Download downLoad, Long startIndex, Long endIndex) {
        this.downLoad = downLoad;
        this.startIndex =startIndex;
        this.endIndex = endIndex;
    }

    public DownloadThread(Download downLoad, int startIndex, int endIndex) {
        this.downLoad = downLoad;
        this.startIndex = (long)startIndex;
        this.endIndex = (long) endIndex;
    }

    /**
     * 分段文件写入
     */
    private void writeFile(InputStream inputStream) throws IOException {
        //随机写文件的时候从哪个位置开始写
        RandomAccessFile raf = new RandomAccessFile(downLoad.getLocalPath(), "rwd");
        //定位文件
        raf.seek(startIndex);

        int len;

        byte[] buffer = new byte[downLoad.getNumberOfBytes()];

        while ((len = inputStream.read(buffer)) != -1) {

            raf.write(buffer, 0, len);
            count += len;
        }

        inputStream.close();
        raf.close();
        System.out.println(Thread.currentThread().getName() + "下载结束");
    }


    @Override
    public void run() {
        try {
            URL url = new URL(downLoad.getServerPath());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //请求服务器下载部分的文件的指定位置
            conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            //支持分段下载
            if (code == 206) {
                //返回资源
                System.out.println(Thread.currentThread().getName()+"下载" + startIndex + "-" + endIndex);
                InputStream inputStream = conn.getInputStream();
                writeFile(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
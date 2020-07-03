package com.fw.utils;

import entity.DownLoad;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * 内部类用于实现下载
 * @author yqf
 */
public class DownLoadThread extends Thread {

    /**
     * 线程ID
     */
    private int threadId;

    /**
     * 下载类
     * */
    private DownLoad downLoad;

    /**
     * 线程计数同步辅助
     */
    private CountDownLatch latch;

    /**
     * 多线程读取字节计数变量
     */
    private int count = 0;
    /**
     * 下载起始位置
     */
    private int startIndex;
    /**
     * 下载结束位置
     */
    private int endIndex;


    public DownLoadThread (int threadId,int startIndex,int endIndex, DownLoad downLoad,CountDownLatch latch){
        this.threadId = threadId;
        this.downLoad = downLoad;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.latch = latch;
    }


    @Override
    public void run() {
        try {
            System.out.println("线程" + threadId + "正在下载...");
            URL url = new URL(downLoad.getServerPath());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //请求服务器下载部分的文件的指定位置
            conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();

            System.out.println("线程" + threadId + "请求返回code=" + code);
            //返回资源
            InputStream is = conn.getInputStream();
            //随机写文件的时候从哪个位置开始写
            RandomAccessFile raf = new RandomAccessFile(downLoad.getLocalPath(), "rwd");
            //定位文件
            raf.seek(startIndex);

            int len = 0;
            byte[] buffer = new byte[downLoad.getNumberOfBytes()];
            while ((len = is.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
                count += len;
            }
            is.close();
            raf.close();
            System.out.println("线程" + threadId + "下载完毕");
            //计数值减一
            latch.countDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
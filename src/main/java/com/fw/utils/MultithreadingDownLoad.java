package com.fw.utils;


import entity.DownLoad;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * @author yqf
 */
public class MultithreadingDownLoad {
    /**
     * 同时下载的线程数
     */
    private int threadCount;

    private DownLoad downLoad;

    private  CountDownLatch latch;


    public MultithreadingDownLoad(int threadCount, DownLoad downLoad,CountDownLatch latch) {
        this.threadCount = threadCount;
        this.downLoad = downLoad;
        this.latch = latch;
    }

    public void executeDownLoad() {

        try {
            URL url = new URL(downLoad.getServerPath());
            //返回URLConnection表示与其引用的远程对象的连接的实例 URL。
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置在打开与此URLConnection引用的资源的通信链接时要使用的指定超时值（以毫秒为单位）。如果超时在可以建立连接之前到期，则引发java.net.SocketTimeoutException。超时为零被解释为无限超时。
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            //它将分别返回200和401。如果无法从响应中识别出代码，则返回-1（即，响应不是有效的HTTP）。
            int code = conn.getResponseCode();
            if (code == 200) {
                //服务器返回的数据的长度，实际上就是文件的长度,单位是字节
                int length = conn.getContentLength();
                System.out.println("文件总长度:" + length + "字节(B)");
                //创建随机访问文件流，以便从File参数指定的文件中读取，也可以选择写入。RandomAccessFile(File file, String mode)
                RandomAccessFile raf = new RandomAccessFile(downLoad.getLocalPath(), "rwd");
                //指定创建的文件的长度
                raf.setLength(length);
                raf.close();
                //分割文件
                int blockSize = length / threadCount;

                System.out.println(blockSize );

                for (int threadId = 1; threadId <= threadCount; threadId++) {
                    //第一个线程下载的开始位置
                    int startIndex = (threadId - 1) * blockSize;
                    int endIndex = startIndex + blockSize - 1;
                    if (threadId == threadCount) {
                        //最后一个线程下载的长度稍微长一点
                        endIndex = length;
                    }
                    System.out.println("线程" + threadId + "下载:" + startIndex + "字节~" + endIndex + "字节");
                    new DownLoadThread(threadId, startIndex, endIndex,downLoad,latch).start();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

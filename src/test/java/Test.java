import com.fw.entity.Download;
import com.fw.entity.MultithreadingDownLoad;
import com.fw.factory.DownloadThreadFactory;
import com.fw.http.HttpHeader;
import com.fw.http.HttpStatus;
import com.fw.utils.HttpHeaderIterator;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        //线程开启数
        int threadSize = 10;
        //分段下载大小
        int numberOfBytes = 1024*1024;
        //线程工厂名
        String threadFactoryName = "download";
        //核心线程数
        int corePoolSize = 10;
        //最大线程数
        int maximumPoolSize = 10;
        //非核心线程保持时间
        int keepAliveTime = 5;
        //服务器地址
        String serverPath = "http://mirrors.aliyun.com/centos/7/isos/x86_64/CentOS-7-x86_64-DVD-2003.iso";
        //本地下载地址
        String localPath = "/home/yqf/下载/CentOS-7-x86_64-DVD-2003.iso";

        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();

        DownloadThreadFactory downloadThreadFactory = new DownloadThreadFactory(threadFactoryName);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                blockingQueue,
                downloadThreadFactory

        );

        Download downLoad = new Download(serverPath,localPath,numberOfBytes);


        MultithreadingDownLoad m = new MultithreadingDownLoad(threadPoolExecutor,threadSize,downLoad);
        m.executeDownLoad();
        m.downloadProgress();


    }
}

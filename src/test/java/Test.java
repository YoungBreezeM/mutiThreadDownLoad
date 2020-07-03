import com.fw.utils.DownLoadThread;
import com.fw.utils.MultithreadingDownLoad;
import entity.DownLoad;

import java.util.concurrent.CountDownLatch;

public class Test {
    public static void main(String[] args) {
        //线程开启数
        int threadSize = 100;

        CountDownLatch latch = new CountDownLatch(threadSize);

        DownLoad downLoad = new DownLoad(
                "http://mirrors.163.com/debian/ls-lR.gz",
                "/home/yqf/下载/ls-lR.gz",
                1024

        );


        MultithreadingDownLoad m = new MultithreadingDownLoad(threadSize,downLoad,latch);
        long startTime = System.currentTimeMillis();
        try {
            m.executeDownLoad();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("全部下载结束,共耗时" + (endTime - startTime) / 1000 + "s");

    }
}

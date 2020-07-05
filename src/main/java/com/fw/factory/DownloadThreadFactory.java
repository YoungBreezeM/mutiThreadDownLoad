package com.fw.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * @author yqf
 */
public class DownloadThreadFactory implements ThreadFactory {

    private int counter;
    private String name;
    private List<String> stats;

    public DownloadThreadFactory(String name) {
        counter = 1;
        this.name = name;
        stats = new ArrayList<String>();
    }

    @Override
    public Thread newThread(Runnable runnable) {

        Thread t = new Thread(runnable, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format(" 线程名: %s 于 %s 开始下载... \n", t.getName(), new Date()));
        return t;
    }

    public String getStats() {
        StringBuilder buffer = new StringBuilder();
        for (String stat : stats) {
            buffer.append(stat);
        }
        return buffer.toString();
    }
}

package entity;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;

/**
 * @author yqf
 */
public class DownLoad implements Serializable {
    /**
     * 下载服务器地址
     * */
    private String serverPath;
    /**
     * 下载本地地址
     * */
    private String localPath;

    @Override
    public String toString() {
        return "DownLoad{" +
                "serverPath='" + serverPath + '\'' +
                ", localPath='" + localPath + '\'' +
                ", numberOfBytes=" + numberOfBytes +
                '}';
    }

    public DownLoad(String serverPath, String localPath, int numberOfBytes) {
        this.serverPath = serverPath;
        this.localPath = localPath;
        this.numberOfBytes = numberOfBytes;
    }

    /**
     * 每次读取字节数
     */
    private int numberOfBytes;

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public int getNumberOfBytes() {
        return numberOfBytes;
    }

    public void setNumberOfBytes(int numberOfBytes) {
        this.numberOfBytes = numberOfBytes;
    }
}

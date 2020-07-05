package com.fw.utils;

/**
 * @author yqf
 */
public class NetSpeed {

    private int bytes;

    private int type = 0;

    public NetSpeed(int bytes) {
        this.bytes = bytes;
    }

    public String compute(){

        while (true){
            if(bytes>=1024){
                bytes = bytes/1024;
                type++;
            }else {
                break;
            }

        }
        switch (type){
            case 0:{
                return bytes+"B/s";
            }
            case 1:
                return bytes+"kB/s";

            case 2:
                return bytes+"MB/s";
            case 3:
                return bytes+"GB/s";
            default:
                return "";
        }
    }
}

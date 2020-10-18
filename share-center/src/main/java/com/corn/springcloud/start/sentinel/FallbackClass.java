package com.corn.springcloud.start.sentinel;

public class FallbackClass {

    public static String fallback(String s,Throwable e){
        System.out.println("被降级了");
        e.printStackTrace();
        return "被降级了 fallback";
    }
}

package com.corn.springcloud.authcenter.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockHandlerClass {

    public static String block(String s, BlockException e){
        System.out.println("被限流了");
//        e.printStackTrace();
        return "被限流了 block";
    }
}

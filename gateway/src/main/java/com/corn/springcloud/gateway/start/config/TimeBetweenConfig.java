package com.corn.springcloud.gateway.start.config;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;


}

class Base {
    private void amethod(int iBase) {
        System.out.println("Base.amethod");
    }
}
class Over extends Base {
    public static void main(String args[]) {
        Over o = new Over();
        int iBase = 0 ;
        o.amethod(iBase) ;
    }
    public void amethod(int iOver) {
        System.out.println("Over.amethod");
    }
}
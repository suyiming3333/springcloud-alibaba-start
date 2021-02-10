//package com.corn.springcloud.start.stream;
//
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.ErrorMessage;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestConsumer {
//
//    @StreamListener(Sink.INPUT)
//    public void OnMessage(String message){
//        System.out.println("stream mesg:"+message);
//        throw new RuntimeException("message parse error");
//    }
//
//    @StreamListener("errorChannel")
//    public void error(Message<?> message){
//        System.out.println("stream mesg:"+(ErrorMessage)message);
//    }
//}

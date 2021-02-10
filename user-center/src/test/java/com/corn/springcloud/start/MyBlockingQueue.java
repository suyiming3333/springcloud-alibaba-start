package com.corn.springcloud.start;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue {

    class MyQueue<T> {

        private Integer maxSize = 10;

        private LinkedList<T> storage = new LinkedList<>();

        public MyQueue(){
            System.out.println("initialized!");
        }

        public synchronized void put(T t) throws InterruptedException {
            //放满了 阻塞生产者
            while (storage.size() == maxSize){
                System.out.println("full");
                this.wait();
            }
            //生产一个通知一个
            storage.add(t);
            this.notify();
            System.out.println("add one");
        }

        public synchronized T take() throws InterruptedException {
            //空，消费阻塞
            while (storage.size() == 0){
                this.wait();
            }
            T poll = storage.poll();
            this.notify();
            return poll;
        }
    }
}

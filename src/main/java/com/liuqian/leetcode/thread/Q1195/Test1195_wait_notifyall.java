package com.liuqian.leetcode.thread.Q1195;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 *
 */
public class Test1195_wait_notifyall {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new Test1195_wait_notifyall().new FizzBuzz(15);
        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.print("fizz,"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.print("buzz,"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.print("fizzbuzz,"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.number(a -> System.out.print(a + ","));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }


    class FizzBuzz {
        private int n;

        private int state = -1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for(int i = 3 ; i <= n ; i = i+3){
                if(i%5 !=0){
                    synchronized (this){
                        while (state != 3){
                            this.wait();
                        }
                        printFizz.run();
                        state = -1;
                        this.notifyAll();
                    }
                }
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for(int i = 5 ; i <= n ; i = i+5){
                if(i%3 !=0){
                    synchronized (this){
                        while (state != 5){
                            this.wait();
                        }
                        printBuzz.run();
                        state = -1;
                        this.notifyAll();
                    }
                }
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for(int i = 15 ; i <= n ; i = i+15){
                synchronized (this){
                    while (state != 15){
                        this.wait();
                    }
                    printFizzBuzz.run();
                    state = -1;
                    this.notifyAll();
                }
            }
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<=n ; i++){
                synchronized (this){
                    while (state != -1){
                        this.wait();
                    }
                    if(i%3 == 0 && i%5 == 0){
                        state = 15;
                        this.notifyAll();
                    }
                    else if(i%3 == 0){
                        state = 3;
                        this.notifyAll();
                    }
                    else if(i%5 == 0){
                        state = 5;
                        this.notifyAll();
                    }
                    else {
                        printNumber.accept(i);
                    }
                }
            }
        }
    }

}

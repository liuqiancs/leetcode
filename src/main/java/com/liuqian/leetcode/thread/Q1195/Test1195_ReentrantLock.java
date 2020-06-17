package com.liuqian.leetcode.thread.Q1195;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 * ReentrantLock
 *
 */
public class Test1195_ReentrantLock {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new Test1195_ReentrantLock().new FizzBuzz(15);
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

        private Lock lock = new ReentrantLock();
        private Condition fc = lock.newCondition();
        private Condition bc = lock.newCondition();
        private Condition fbc = lock.newCondition();
        private Condition nc = lock.newCondition();
        private int state = -1;



        public FizzBuzz(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for(int i = 3 ; i <= n ; i = i+3){
                if(i%5 !=0){
                    try {
                        lock.lock();
                        if(state != 3){
                            fc.await();
                        }
                        printFizz.run();
                        state = -1;
                        nc.signal();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for(int i = 5 ; i <= n ; i = i+5){
                if(i%3 !=0){
                    try {
                        lock.lock();
                        if(state != 5){
                            bc.await();
                        }
                        printBuzz.run();
                        state = -1;
                        nc.signal();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for(int i = 15 ; i <= n ; i = i+15){
                try {
                    lock.lock();
                    if(state != 15){
                        fbc.await();
                    }
                    printFizzBuzz.run();
                    state = -1;
                    nc.signal();
                }finally {
                    lock.unlock();
                }
            }
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<=n ; i++){
                try {
                    lock.lock();
                    if (state != -1){
                        nc.await();
                    }
                    if(i%3 == 0 && i%5 == 0){
                        state = 15;
                        fbc.signal();
                    }
                    else if(i%3 == 0){
                        state = 3;
                        fc.signal();
                    }
                    else if(i%5 == 0){
                        state = 5;
                        bc.signal();
                    }
                    else {
                        printNumber.accept(i);
                    }
                }finally {
                    lock.unlock();
                }

            }
        }
    }

}

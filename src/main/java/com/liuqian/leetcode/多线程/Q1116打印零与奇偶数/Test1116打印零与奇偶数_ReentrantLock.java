package com.liuqian.leetcode.多线程.Q1116打印零与奇偶数;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 1116. 打印零与奇偶数
 * 参考
 * ReentrantLock
 */
public class Test1116打印零与奇偶数_ReentrantLock {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new Test1116打印零与奇偶数_ReentrantLock().new ZeroEvenOdd(6);

        new Thread(() -> {
            try {
                zeroEvenOdd.odd(a -> System.out.print(a));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.zero(a -> System.out.print(a));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                zeroEvenOdd.even(a -> System.out.print(a));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }



    class ZeroEvenOdd {
        private int n;

        private Lock lock = new ReentrantLock();
        private Condition zc = lock.newCondition();
        private Condition ec = lock.newCondition();
        private Condition oc = lock.newCondition();
        private int state = 0;


        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<= n; i++){
                try {
                    lock.lock();
                    while(state != 0){
                        zc.await();
                    }
                    printNumber.accept(0);
                    if(i%2 == 0){
                        state = 2;
                        ec.signal();
                    }else{
                        state = 1;
                        oc.signal();
                    }
                }finally {
                    lock.unlock();
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2 ; i<=n ; i = i+ 2){
                try {
                    lock.lock();
                    while(state != 2){
                        ec.await();
                    }
                    printNumber.accept(i);
                    state = 0;
                    zc.signal();
                }finally {
                    lock.unlock();
                }
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<=n ; i = i+ 2){
                try {
                    lock.lock();
                    while(state != 1){
                        oc.await();
                    }
                    printNumber.accept(i);
                    state = 0;
                    zc.signal();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

}

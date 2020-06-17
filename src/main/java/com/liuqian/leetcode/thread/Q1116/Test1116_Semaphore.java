package com.liuqian.leetcode.thread.Q1116;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 1116. 打印零与奇偶数
 * 参考
 * 信号量
 */
public class Test1116_Semaphore {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new Test1116_Semaphore().new ZeroEvenOdd(1);

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

        Semaphore zs = new Semaphore(1);
        Semaphore es = new Semaphore(0);
        Semaphore os = new Semaphore(0);


        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<= n; i++){
                zs.acquire();
                printNumber.accept(0);
                if(i%2 == 0){
                    es.release();
                }else{
                    os.release();
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2 ; i<=n ; i = i+ 2){
                es.acquire();
                printNumber.accept(i);
                zs.release();
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<=n ; i = i+ 2){
                os.acquire();
                printNumber.accept(i);
                zs.release();
            }
        }
    }

}

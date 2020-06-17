package com.liuqian.leetcode.thread.Q1116;
import java.util.function.IntConsumer;

/**
 * 1116. 打印零与奇偶数
 * 参考
 * volatile
 */
public class Test1116_nolock {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new Test1116_nolock().new ZeroEvenOdd(10);

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

        private volatile int state = 0;


        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<= n; i++){
                while (state != 0){Thread.yield();}
                printNumber.accept(0);
                if(i%2 == 0){
                    state = 2;
                }else{
                    state = 1;
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2 ; i<=n ; i = i+ 2){
                while (state != 2){Thread.yield();}
                printNumber.accept(i);
                state = 0;
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<=n ; i = i+ 2){
                while (state != 1){Thread.yield();}
                printNumber.accept(i);
                state = 0;
            }
        }
    }

}

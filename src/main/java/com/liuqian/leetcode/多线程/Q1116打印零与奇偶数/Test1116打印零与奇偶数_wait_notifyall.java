package com.liuqian.leetcode.多线程.Q1116打印零与奇偶数;
import java.util.function.IntConsumer;

/**
 * 1116. 打印零与奇偶数
 * 参考
 */
public class Test1116打印零与奇偶数_wait_notifyall {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new Test1116打印零与奇偶数_wait_notifyall().new ZeroEvenOdd(6);

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

        private int state = 0;


        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<= n; i++){
                synchronized (this){
                    while(state != 0){
                        this.wait();
                    }
                    printNumber.accept(0);
                    if(i%2 == 0){
                        state = 2;
                        this.notifyAll();
                    }else{
                        state = 1;
                        this.notifyAll();
                    }
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2 ; i<=n ; i = i+ 2){
                synchronized (this){
                    while(state != 2){
                        this.wait();
                    }
                    printNumber.accept(i);
                    state = 0;
                    this.notifyAll();
                }
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1 ; i<=n ; i = i+ 2){
                synchronized (this){
                    while(state != 1){
                        this.wait();
                    }
                    printNumber.accept(i);
                    state = 0;
                    this.notifyAll();
                }
            }
        }
    }

}

package com.liuqian.leetcode.thread.Q1195;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 * 参考：https://leetcode-cn.com/problems/fizz-buzz-multithreaded/solution/javashi-xian-shi-yong-semaphore-huo-zhe-reentrantl/
 * 信号量
 *
 */
public class Test1195_Semaphore {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new Test1195_Semaphore().new FizzBuzz(15);
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

        Semaphore semaphoreOther = new Semaphore(1);
        Semaphore semaphore3 = new Semaphore(0);
        Semaphore semaphore5 = new Semaphore(0);
        Semaphore semaphore15 = new Semaphore(0);


        public FizzBuzz(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 3 ;i <= n ;i=i+3){
                if (i % 5 != 0) {
                    semaphore3.acquire();
                    printFizz.run();
                    semaphoreOther.release();
                }
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 5 ;i <= n ;i=i+5){
                if (i % 3 != 0) {
                    semaphore5.acquire();
                    printBuzz.run();
                    semaphoreOther.release();
                }
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 15 ;i <= n ;i=i+15){
                semaphore15.acquire();
                printFizzBuzz.run();
                semaphoreOther.release();
            }
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1 ;i <= n ;i++){
                semaphoreOther.acquire();
                if(i%3 != 0 && i%5 != 0){
                    printNumber.accept(i);
                }
                if(i % 15 == 0){
                    semaphore15.release();
                }
                else if(i % 5 == 0){
                    semaphore5.release();
                }
                else if(i % 3 == 0){
                    semaphore3.release();
                }
                else {
                    semaphoreOther.release();
                }
            }
        }
    }

}

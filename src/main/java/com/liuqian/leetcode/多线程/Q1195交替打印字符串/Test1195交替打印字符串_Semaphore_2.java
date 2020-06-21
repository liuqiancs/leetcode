package com.liuqian.leetcode.多线程.Q1195交替打印字符串;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 * 参考：https://leetcode-cn.com/problems/fizz-buzz-multithreaded/solution/shi-yong-4ge-xin-hao-liang-jie-jue-by-chenfan/
 * 信号量
 *
 */
public class Test1195交替打印字符串_Semaphore_2 {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new Test1195交替打印字符串_Semaphore_2().new FizzBuzz(15);
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

        Semaphore fizzSem = new Semaphore(0);

        Semaphore buzzSem = new Semaphore(0);

        Semaphore fizzBuzzSem = new Semaphore(0);

        Semaphore numSem = new Semaphore(0);

        private int n;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz". 3
        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0 && i % 5 != 0) {
                    fizzSem.acquire();
                    printFizz.run();
                    numSem.release();
                }
            }
        }

        // printBuzz.run() outputs "buzz". 5
        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 5 == 0 && i % 3 != 0) {
                    buzzSem.acquire();
                    printBuzz.run();
                    numSem.release();
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0 && i % 5 == 0) {
                    fizzBuzzSem.acquire();
                    printFizzBuzz.run();
                    numSem.release();
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0 && i % 5 != 0) {
                    fizzSem.release();
                    numSem.acquire();
                } else if (i % 5 == 0 && i % 3 != 0) {
                    buzzSem.release();
                    numSem.acquire();
                } else if (i % 3 == 0 && i % 5 == 0) {
                    fizzBuzzSem.release();
                    numSem.acquire();
                } else {
                    printNumber.accept(i);
                }
            }
        }

    }
}

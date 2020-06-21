package com.liuqian.leetcode.多线程.Q1195交替打印字符串;

import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 * 参考：https://leetcode-cn.com/problems/fizz-buzz-multithreaded/solution/1ge-reentrantlock-1ge-condition-1ge-volatilebian-l/
 * 无锁+while忙等
 * state必须声明为volatile，否则其他线程不可见
 *
 */
public class Test1195交替打印字符串_nolock {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new Test1195交替打印字符串_nolock().new FizzBuzz(15);
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
        private volatile int state = -1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 3; i <= n; i += 3) {   //只输出3的倍数(不包含15的倍数)
                if (i % 15 == 0)    //15的倍数不处理，交给fizzbuzz()方法处理
                    continue;
                while (state != 3) {}
                printFizz.run();
                state = -1;    //控制权交还给number()方法
            }
        }

        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 5; i <= n; i += 5) {   //只输出5的倍数(不包含15的倍数)
                if (i % 15 == 0)    //15的倍数不处理，交给fizzbuzz()方法处理
                    continue;
                while (state != 5){}
                printBuzz.run();
                state = -1;    //控制权交还给number()方法
            }
        }

        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 15; i <= n; i += 15) {   //只输出15的倍数
                while (state != 15){}
                printFizzBuzz.run();
                state = -1;    //控制权交还给number()方法
            }
        }

        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; ++i) {
                while (state != -1){}
                if (i % 3 != 0 && i % 5 != 0)
                    printNumber.accept(i);
                else {
                    if (i % 15 == 0)
                        state = 15;    //交给fizzbuzz()方法处理
                    else if (i % 5 == 0)
                        state = 5;    //交给buzz()方法处理
                    else
                        state = 3;    //交给fizz()方法处理
                }
            }
        }
    }
}

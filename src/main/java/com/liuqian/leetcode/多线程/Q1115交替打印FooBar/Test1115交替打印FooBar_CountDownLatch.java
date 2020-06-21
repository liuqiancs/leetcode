package com.liuqian.leetcode.多线程.Q1115交替打印FooBar;

import java.util.concurrent.CountDownLatch;

/**
 * 1115. 交替打印FooBar
 * 参考 https://leetcode-cn.com/problems/print-foobar-alternately/solution/yong-liang-ge-countdownlatchhu-xiang-awaitji-ke-by/
 * CountDownLatch
 */
public class Test1115交替打印FooBar_CountDownLatch {
    public static void main(String[] args) {
        FooBar fooBar = new Test1115交替打印FooBar_CountDownLatch().new FooBar(10);

        new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }


    class FooBar {
        private int n;

        private CountDownLatch foo = new CountDownLatch(0);
        private CountDownLatch bar = new CountDownLatch(1);

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                foo.await();
                printFoo.run();
                foo = new CountDownLatch(1);
                bar.countDown();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                bar.await();
                printBar.run();
                bar = new CountDownLatch(1);
                foo.countDown();
            }
        }
    }
}

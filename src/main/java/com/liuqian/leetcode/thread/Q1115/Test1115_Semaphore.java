package com.liuqian.leetcode.thread.Q1115;

import java.util.concurrent.Semaphore;

/**
 * 1115. 交替打印FooBar
 * 参考 https://leetcode-cn.com/problems/print-foobar-alternately/solution/liang-ge-xin-hao-liang-jiao-ti-kong-zhi-by-atizose/
 * 信号量
 */
public class Test1115_Semaphore {
    public static void main(String[] args) {
        FooBar fooBar = new Test1115_Semaphore().new FooBar(10);

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

        private Semaphore foo = new Semaphore(1);
        private Semaphore bar = new Semaphore(0);


        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                foo.acquire();
                printFoo.run();
                bar.release();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                bar.acquire();
                printBar.run();
                foo.release();
            }
        }
    }
}

package com.liuqian.leetcode.thread.Q1114;

import java.util.concurrent.CountDownLatch;

/**
 *1114. 按序打印
 * 参考 https://leetcode-cn.com/problems/print-in-order/solution/javabing-fa-gong-ju-lei-da-lian-bing-by-kevinbauer/
 * CountDownLatch
 */
public class Test1114_CountDownLatch {
    public static void main(String[] args) throws Exception {
        Foo foo = new Test1114_CountDownLatch().new Foo();
        new Thread(() -> foo.second(() -> System.out.print("two"))).start();
        new Thread(() -> foo.first(() -> System.out.print("one"))).start();
        new Thread(() -> foo.third(() -> System.out.print("three"))).start();
    }

    class Foo {
        CountDownLatch stage2 = new CountDownLatch(1);
        CountDownLatch stage3 = new CountDownLatch(1);

        public Foo() {
        }

        public void first(Runnable printFirst) {
            printFirst.run();
            stage2.countDown();
        }

        public void second(Runnable printSecond) {
            try {
                stage2.await();
                printSecond.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                stage3.countDown();
            }
        }

        public void third(Runnable printThird) {
            try {
                stage3.await();
                printThird.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
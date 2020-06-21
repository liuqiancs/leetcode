package com.liuqian.leetcode.多线程.Q1114按序打印;

import java.util.concurrent.Semaphore;

/**
 *1114. 按序打印
 * 参考 https://leetcode-cn.com/problems/print-in-order/solution/javabing-fa-gong-ju-lei-da-lian-bing-by-kevinbauer/
 * 信号量
 */
public class Test1114按序打印_Semaphore {
    public static void main(String[] args) throws Exception {
        Foo foo = new Test1114按序打印_Semaphore().new Foo();
        new Thread(() -> foo.second(() -> System.out.print("two"))).start();
        new Thread(() -> foo.first(() -> System.out.print("one"))).start();
        new Thread(() -> foo.third(() -> System.out.print("three"))).start();
    }

    class Foo {
        Semaphore stage2 = new Semaphore(0);
        Semaphore stage3 = new Semaphore(0);

        public Foo() {
        }

        public void first(Runnable printFirst) {
            printFirst.run();
            stage2.release();
        }

        public void second(Runnable printSecond) {
            try {
                stage2.acquire();
                printSecond.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                stage3.release();
            }
        }

        public void third(Runnable printThird) {
            try {
                stage3.acquire();
                printThird.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {

            }
        }

    }
}
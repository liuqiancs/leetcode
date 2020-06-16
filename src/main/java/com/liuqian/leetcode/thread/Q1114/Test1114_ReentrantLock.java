package com.liuqian.leetcode.thread.Q1114;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *1114. 按序打印
 * 参考 https://leetcode-cn.com/problems/print-in-order/solution/javabing-fa-gong-ju-lei-da-lian-bing-by-kevinbauer/
 * ReentrantLock
 * 比wait notifyAll的优点，就是可以通知指定等待队列
 */
public class Test1114_ReentrantLock {
    public static void main(String[] args) throws Exception {
        Foo foo = new Test1114_ReentrantLock().new Foo();
        new Thread(() -> foo.second(() -> System.out.print("two"))).start();
        new Thread(() -> foo.first(() -> System.out.print("one"))).start();
        new Thread(() -> foo.third(() -> System.out.print("three"))).start();
    }

    class Foo {
        Lock lock = new ReentrantLock();
        // 方法执行标识 1 标识已经执行完first方法，允许second方法执行 2 标识已经执行完second方法，允许third方法执行
        private int flag = 0;
        Condition stage2 = lock.newCondition();
        Condition stage3 = lock.newCondition();
        public Foo() {
        }

        public void first(Runnable printFirst) {
            try {
                lock.lock();
                printFirst.run();
                flag = 1;
                stage2.signal();
            }finally {
                lock.unlock();
            }
        }

        public void second(Runnable printSecond) {
            try {
                lock.lock();
                while(flag!=1) {
                    stage2.await();
                }
                printSecond.run();
                flag = 2;
                stage3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void third(Runnable printThird) {
            try {
                lock.lock();
                while(flag!=2) {
                    stage3.await();
                }
                printThird.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
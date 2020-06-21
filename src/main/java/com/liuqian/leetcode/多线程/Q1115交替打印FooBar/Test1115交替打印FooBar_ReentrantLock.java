package com.liuqian.leetcode.多线程.Q1115交替打印FooBar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1115. 交替打印FooBar
 * 参考 https://leetcode-cn.com/problems/print-foobar-alternately/solution/li-yong-reentrantlockhe-conditionshi-xian-by-cr6-2/
 * ReentrantLock
 */
public class Test1115交替打印FooBar_ReentrantLock {
    public static void main(String[] args) {
        FooBar fooBar = new Test1115交替打印FooBar_ReentrantLock().new FooBar(10);

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
        private ReentrantLock lock = new ReentrantLock();
        private Condition fooCondition = lock.newCondition();
        private Condition barCondition = lock.newCondition();
        private boolean isBar = false;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                try {
                    lock.lock();
                    if(isBar){
                        fooCondition.await();
                    }
                    printFoo.run();
                    isBar = true;
                    barCondition.signal();
                }
                finally {
                    lock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                try {
                    lock.lock();
                    if(!isBar){
                        barCondition.await();
                    }
                    printBar.run();
                    isBar = false;
                    fooCondition.signal();
                }
                finally {
                    lock.unlock();
                }
            }
        }
    }
}

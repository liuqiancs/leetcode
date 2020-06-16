package com.liuqian.leetcode.thread.Q1115;

/**
 * 1115. 交替打印FooBar
 */
public class Test1115_wait_notify {
    public static void main(String[] args) {
        FooBar fooBar = new Test1115_wait_notify().new FooBar(10);

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
        private boolean barstarted = false;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this){
                    while (barstarted){
                        this.wait();
                    }
                    printFoo.run();
                    barstarted = true;
                    this.notify();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this){
                    while (!barstarted){
                        this.wait();
                    }
                    printBar.run();
                    barstarted = false;
                    this.notify();
                }
            }
        }
    }
}

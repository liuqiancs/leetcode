package com.liuqian.leetcode.多线程.Q1115交替打印FooBar;

/**
 * 1115. 交替打印FooBar
 * 参考：https://leetcode-cn.com/problems/print-foobar-alternately/solution/wu-suo-qie-zui-jian-dan-zui-rong-yi-li-jie-de-shi-/
 * 无锁+while忙等
 * printBarSwitch必须声明为volatile，否则其他线程不可见
 *
 */
public class Test1115交替打印FooBar_nolock {
    public static void main(String[] args) {
        FooBar fooBar = new Test1115交替打印FooBar_nolock().new FooBar(10);

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

        private volatile boolean printBarSwitch = false;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                while(printBarSwitch) {Thread.yield();}
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                printBarSwitch = true;
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                while(!printBarSwitch) {Thread.yield();}
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                printBarSwitch = false;
            }
        }
    }
}

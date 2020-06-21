package com.liuqian.leetcode.多线程.Q1114按序打印;

/**
 *1114. 按序打印
 *
 * wait_notifyAll
 */
public class Test1114按序打印_wait_notifyall {
    public static void main(String[] args) throws Exception {
        Foo foo = new Test1114按序打印_wait_notifyall().new Foo();
        new Thread(() -> foo.first(() -> System.out.print("one"))).start();
        new Thread(() -> foo.second(() -> System.out.print("two"))).start();
        new Thread(() -> foo.third(() -> System.out.print("three"))).start();
    }

    class Foo {

        // 方法执行标识 1 标识已经执行完first方法，允许second方法执行 2 标识已经执行完second方法，允许third方法执行
        private int flag = 0;

        public Foo() {

        }

        public void first(Runnable printFirst) {
            synchronized (this){
                printFirst.run();
                flag = 1;
                this.notifyAll();
            }
        }

        public void second(Runnable printSecond) {
            synchronized (this){
                while (flag != 1){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printSecond.run();
                flag = 2;
                this.notifyAll();
            }
        }

        public void third(Runnable printThird) {
            synchronized (this){
                while (flag != 2){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printThird.run();
                this.notifyAll();
            }
        }
    }
}
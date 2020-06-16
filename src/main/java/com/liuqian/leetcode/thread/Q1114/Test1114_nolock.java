package com.liuqian.leetcode.thread.Q1114;

/**
 *1114. 按序打印
 * 参考 https://leetcode-cn.com/problems/print-in-order/solution/javabing-fa-gong-ju-lei-da-lian-bing-by-kevinbauer/
 * 无锁+while忙等
 * flag必须声明为volatile，否则其他线程不可见
 * while忙等会一定程度上加重cpu的负担，对于job执行时间很短的情况，该算法比较合适
 */
public class Test1114_nolock {
    public static void main(String[] args) throws Exception {
        Foo foo = new Test1114_nolock().new Foo();
        new Thread(() -> foo.second(() -> System.out.print("two"))).start();
        new Thread(() -> foo.first(() -> System.out.print("one"))).start();
        new Thread(() -> foo.third(() -> System.out.print("three"))).start();
    }

    class Foo {
        // 方法执行标识 1 标识已经执行完first方法，允许second方法执行 2 标识已经执行完second方法，允许third方法执行
        private volatile int flag = 0;

        public Foo() {
        }

        public void first(Runnable printFirst) {
            printFirst.run();
            flag = 1;
        }

        public void second(Runnable printSecond) {
            while (flag != 1){
                //等待printFirst完成
            }
            printSecond.run();
            flag = 2;
        }

        public void third(Runnable printThird) {
            while (flag != 2){
                //等待printSecond完成
            }
            printThird.run();
        }

    }
}
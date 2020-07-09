package com.fz;

public class ThreadFezo extends Thread {

    static int target = 0;
    public static Object obj;//必须加static，否则每个线程都会产生一个obj，这样加锁就没有意义了

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (target < 100) {
            synchronized (obj) {//obj没有任何意义，就是为了保证多个线程使用的是同一个锁
                //加一个if语句，保证在票数卖到100张以后，等待着的线程不会继续执行target++代码。
                if (target < 100) {
                    target++;
                    System.out.println(Thread.currentThread().getName() + "卖出了" + target + "张票");
                }
                else {
                    System.out.println("卖完了");
                }
            }
        }
    }
}

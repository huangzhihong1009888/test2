package com.jiguang.test.controller;

/**
 * @ClassName MyThreadPrinter2
 * @Description TODO
 * @Author user
 * @Date 2020/6/24 16:30
 */
public class MyThreadPrinter2 implements Runnable{
    private String name;
    private Object prev;
    private Object self;

    private MyThreadPrinter2(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 3;
        while (count > 0) {
            synchronized (prev) {
                System.out.println("-------------------------------------------");
                System.out.println("获得"+prev+"对象锁");
                synchronized (self) {
                    System.out.println("获得"+self+"对象锁");
                    System.out.println(name);
                    count--;
                    System.out.println("释放"+self+"并唤醒在"+self+"等待线程");
                    self.notify();

                }

                try {
                    System.out.println("释放"+prev+"对象锁 该线程进入等待被唤醒");
                    prev.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("douwancheng");
    }

    public static void main(String[] args) throws Exception {
        Object a = 1;
        Object b = 2;
        Object c = 3;
        MyThreadPrinter2 pa = new MyThreadPrinter2("A", a, b);
        MyThreadPrinter2 pb = new MyThreadPrinter2("B", b, c);
        MyThreadPrinter2 pc = new MyThreadPrinter2("C", c, a);


        new Thread(pa).start();
        Thread.sleep(100);  //确保按顺序A、B、C执行
        new Thread(pb).start();
        Thread.sleep(100);
        new Thread(pc).start();
        Thread.sleep(100);
    }
}

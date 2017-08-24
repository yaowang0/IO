package com.expgiga.juc;

/**
 * 生产者和消费者案例:等待唤醒机制。
 */
public class TestProductAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Productor pro = new Productor(clerk);

        Consumer cus = new Consumer(clerk);

        new Thread(pro, "生产者A").start();
        new Thread(cus, "消费者B").start();

        new Thread(pro, "生产者C").start();
        new Thread(cus, "消费者D").start();

    }
}

//店员
class Clerk {
    private int product = 0;

    //进货
    public synchronized void get() {//
        while (product >= 1) { //为了避免虚假唤醒问题，应该总是使用在循环中
            System.out.println("产品已经满!");

            try {
                this.wait(); //释放锁资源
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName() + " : " + ++product);
        this.notifyAll();
    }

    //卖货
    public synchronized void sale() {//
        while (product <= 0) {
            System.out.println("缺货！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " : " + --product);
        this.notifyAll();
    }
}

//生产者
class Productor implements Runnable {

    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }

}
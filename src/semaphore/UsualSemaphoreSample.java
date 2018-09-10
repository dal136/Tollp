package semaphore;

import java.util.concurrent.Semaphore;

/**
 * <pre>
 * 在车站、机场等出租车时，当很多空出租车就位时，为防止过度拥挤，调度员指挥排队等待坐车的队伍一次进来5个人上车，
 * 等这5个人坐车出发，再放进去下一批。
 * 信号量Semaphore实现以上案例
 * </pre>
 *
 * @author : penghaibing
 * @date 2018/9/10 19:17.
 */
public class UsualSemaphoreSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Action...Go!");
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new SemaphoreWork(semaphore));
            t.start();
        }
    }
}

class SemaphoreWork implements Runnable {
    private String name;
    private Semaphore semaphore;

    public SemaphoreWork(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            log("is waiting for a permit!");
            semaphore.acquire();
            log("acquire a permit!");
            log("executed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log("released a permit!");
            semaphore.release();
        }
    }

    private void log(String msg) {
        if (name == null) {
            name = Thread.currentThread().getName();
        }
        System.out.println(name + " " + msg);
    }
}
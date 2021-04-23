package com.hb0730.spring.boot.sample.redis.integration.lock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

@SpringBootTest(classes = RedisIntegrationLockSampleApplication.class)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class RedisIntegrationLockSampleApplicationTest {
    @Autowired
    private RedisConnectionFactory factory;
    private static final String PREFIX = "test";
    RedisLockRegistry registry = null;

    @Before
    public void before() {
        registry = new RedisLockRegistry(factory, PREFIX, 1000 * 60);
    }

    @Test
    public void testLock() {
        Lock lock = registry.obtain("foo1");
        lock.lock();

        lock = registry.obtain("foo2");
        lock.unlock();
    }
    @Test
    public void testLock2() throws InterruptedException {
        Lock lock = registry.obtain("foo1");
        lock.lock();
        lock.lock();
         new Thread(()->{
             lock.lock();
             lock.unlock();
             try {
                 Thread.sleep(300000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }).start();
        Thread.sleep(300000);
    }

    @Test
    public void testThreadLocalListLeaks() {
        for (int i = 0; i < 100; i++) {
            registry.obtain("foo" + i);
        }
        for (int i = 0; i < 100; i++) {
            Lock lock = registry.obtain("foo" + i);
            lock.lock();
        }
        for (int i = 0; i < 100; i++) {
            Lock lock = registry.obtain("foo" + i);
            lock.unlock();
        }
    }

    @Test
    public void testThread() throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                100,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    AtomicInteger count = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "test-thread-" + count.getAndIncrement());
                    }
                });
        for (int i = 0; i < 25; i++) {
            poolExecutor.execute(new Task("wanwanpp"));
            poolExecutor.execute(new Task("玩玩跑跑"));
            poolExecutor.execute(new Task("123456"));
            poolExecutor.execute(new Task("快过年啦！"));
        }

        Thread.sleep(20000L);
    }

    class Task implements Runnable {
        private final String printThings;
        private final Lock lock = registry.obtain("foo");

        public Task(String printThings) {
            this.printThings = printThings;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000L);
                    lock.lock();
                    for (int i = 0; i < printThings.length(); i++) {
                        System.out.print(printThings.charAt(i));
                        Thread.sleep(300);
                    }
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

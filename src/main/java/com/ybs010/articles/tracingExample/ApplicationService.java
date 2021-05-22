package com.ybs010.articles.tracingExample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@EnableAsync
@Slf4j
public class ApplicationService {

    @Autowired
    private Executor executor;

    public void doSomethingInSameThread() throws InterruptedException {
        Thread.sleep(10);
        log.info("After work...!!");
    }

    public void doSomethingInAnotherThread() {
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Doing some work in new thread");
        };
        executor.execute(runnable);

        log.info("Back to original thread");
    }

    @Async
    public void doSomethingInAsyncThread() {
        log.info("Doing some work in async thread");
    }

    public void doSomethingInCallableAsyncThread(TraceableExecutorService executor) {
        log.info("Doing some work in async thread");
        CompletableFuture.supplyAsync(() -> {
            log.info("In callable async thread");
            return null;
        },executor);
        log.info("After callable async thread");
    }

}

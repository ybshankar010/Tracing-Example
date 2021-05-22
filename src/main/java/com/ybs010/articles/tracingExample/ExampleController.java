package com.ybs010.articles.tracingExample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class ExampleController {

    @Autowired
    private ApplicationService service;

    @Autowired
    private TraceableExecutorService executorService;

    @GetMapping("/nothreads")
    public HttpStatus noThreads() throws InterruptedException {
        log.info("Before work...!!");
        service.doSomethingInSameThread();
        return HttpStatus.OK;
    }

    @GetMapping("/syncThread")
    public HttpStatus syncThread() {
        log.info("Before threading..");
        service.doSomethingInAnotherThread();
        return HttpStatus.OK;
    }

    @GetMapping("/asyncThread")
    public HttpStatus asyncThread() {
        log.info("Before Async thread");
        service.doSomethingInAsyncThread();
        log.info("After Async thread..");
        return HttpStatus.OK;
    }

    @GetMapping("/callableAsync")
    public HttpStatus callableAsync() {
        log.info("Before callable Async thread");
        service.doSomethingInCallableAsyncThread(executorService);
        log.info("After callable Async thread..");
        return HttpStatus.OK;
    }

}

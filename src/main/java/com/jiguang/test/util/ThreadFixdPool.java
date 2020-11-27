package com.jiguang.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadFixdPool {
  private static ExecutorService executor;

  private ThreadFixdPool() {
    super();
  }
  //用此方法取executor
  public static ExecutorService getFixedExecutor(int nThreads){
    if (executor==null||executor.isShutdown()) {
      synchronized (ThreadFixdPool.class) {
    	executor=null;
        executor = Executors.newFixedThreadPool(nThreads);
      }
    }
    return executor;
  }
  public static void main(String[] args) {
      ExecutorService executorService=ThreadFixdPool.getFixedExecutor(100);
      List<Future> list = new ArrayList<>();
      for (int a =0;a<100; a++) {
          final int finalA = a;
          Future future = executorService.submit(()-> System.out.println(finalA));
          list.add(future);
      }
      System.out.println("-----------------"+list.size());
      executorService.shutdown();
  }
 
  
}

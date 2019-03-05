package com.songshuang.learn.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

public class ObserveCommand extends HystrixCommand<Integer> {

  private final int num;

  public ObserveCommand(final int num) {
    super(HystrixCommandGroupKey.Factory.asKey("ObserveGroup"));
    this.num = num;
  }

  @Override
  protected Integer run() throws Exception {
    return null;
  }

  public static void main(String[] args) throws InterruptedException {
    for (int i=0; i<100; i++) {
      final int num = i;
      new Thread(() -> {
        ObserveCommand executeCommand = new ObserveCommand(num);
        // 这个使用的是异步的方式进行处理
        Observable<Integer> observe = executeCommand.observe();
        System.out.println();
      }).start();
    }

    Thread.sleep(100000L);
  }
}

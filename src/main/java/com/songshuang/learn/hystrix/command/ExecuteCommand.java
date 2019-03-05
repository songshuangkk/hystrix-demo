package com.songshuang.learn.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * execute这个调用处理的方式是调用一次command就进行一次阻塞，等待响应结果.
 */
public class ExecuteCommand extends HystrixCommand<Integer> {

  private final int num;

  public ExecuteCommand(final int num) {
    super(HystrixCommandGroupKey.Factory.asKey("executeGroup"));
    this.num = num;
  }

  @Override
  protected Integer run() throws Exception {
    if (num > 20) {
      throw new InterruptedException("Interrupted....");
    }
    return num;
  }

  @Override
  public Integer getFallback() {
    System.out.println();
    System.out.printf("[num = %d] Execute Fallback...\n", num);
    System.out.println();
    return 0;
  }

  public static void main(String[] args) throws InterruptedException {
    for (int i=0; i<100; i++) {
      final int num = i;
      new Thread(() -> {
        ExecuteCommand executeCommand = new ExecuteCommand(num);
        // 这个操作同步阻塞的操作方式，调用一次，执行一次.
        Integer result = executeCommand.execute();
        System.out.println();
        System.out.printf("[num = %d ; result = %d]\n", num, result);
        System.out.println();
      }).start();
    }

    Thread.sleep(100000L);
  }
}

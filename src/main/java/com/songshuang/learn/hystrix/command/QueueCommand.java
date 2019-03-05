package com.songshuang.learn.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class QueueCommand extends HystrixCommand<Integer> {

  private final int num;

  public QueueCommand(final int num) {
    super(HystrixCommandGroupKey.Factory.asKey("queue command"));
    this.num = num;
  }

  @Override
  protected Integer run() throws Exception {
    System.out.printf("[task num = %d\n]", num);
    return num;
  }

  @Override
  public Integer getFallback() {
    System.out.println("Queue Command Fallback");
    return 0;
  }

  public static void main(String[] args) throws InterruptedException {
    for (int i=0; i<100; i++) {
      final int num = i;
      new Thread(() -> {
        QueueCommand executeCommand = new QueueCommand(num);
        // 这个使用的是异步的方式进行处理
        Future<Integer> result = executeCommand.queue();
        System.out.println();
        if (num == 10) {
          try {
            System.out.printf("[taskState = %s ; num = %d ; result = %d]\n", result.isDone(), num, result.get());
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        }
        System.out.println();
      }).start();
    }

    Thread.sleep(100000L);
  }
}

package com.songshuang.learn.hystrix.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class CommandHelloTest {

  @Test
  public void normal() {
    String s = new CommandHelloWorld("Bob").execute();

    assert s.equals("Hello Bob!");
  }

  @Test
  public void futureTest() throws ExecutionException, InterruptedException {
    Future<String> s = new CommandHelloWorld("Bob").queue();

    assert s.get().equals("Hello Bob!");
  }

  @Test
  public void observableTest() {
    Observable<String> s = new CommandHelloWorld("Bob").observe();

    assert s.toBlocking().first().equals("Hello Bob!");
  }

}

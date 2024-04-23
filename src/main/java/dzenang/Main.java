package dzenang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException {

    List<FutureTask<Integer>> randomNumberTasks = new ArrayList<>(15);

    for (int i = 0; i < 16; i++) {
      // create Callable object
      RandomNumber randomNumber = new RandomNumber();

      // create FutureTask object (implementing Future and Runnable)
      FutureTask<Integer> randomNumberTask = new FutureTask<>(randomNumber);
      randomNumberTasks.add(randomNumberTask);

      // create and start a Thread
      Thread randomNumberThread = new Thread(randomNumberTask);
      randomNumberThread.start();

      // adding sleep to see that threads are started one after the another
//      Thread.sleep(1000);
    }
    printResults(randomNumberTasks);

    // dummy example with String Callable
    Callable<String> stringCallable = new Callable<String>() {
      @Override
      public String call() throws Exception {
        // this sleep call is simulating some "big work" to do
        Thread.sleep(1000);
        return "This is return string from stringCallable";
      }
    };
    FutureTask<String> stringFutureTask = new FutureTask<>(stringCallable);
    Thread thread = new Thread(stringFutureTask);
    thread.start();

    try {
      System.out.println(stringFutureTask.get(200, TimeUnit.MILLISECONDS));
    } catch (TimeoutException e) {
      System.out.println("Didn't get the result back from the thread in expected time period. We will try to read result later.");
    }

    System.out.println("This printout is simulating some other work");

    try {
      System.out.println(stringFutureTask.get(200, TimeUnit.MILLISECONDS));
    } catch (TimeoutException e) {
      System.out.println("Didn't get the result even after more time passed.");
    }

    thread.join();
    System.out.println(stringFutureTask.get());
  }

  private static void printResults(List<FutureTask<Integer>> futureTasks)
      throws ExecutionException, InterruptedException {
    for (FutureTask futureTask : futureTasks) {
      Integer result = (Integer) futureTask.get();
      System.out.println("The random number is " + result);
    }
  }
}
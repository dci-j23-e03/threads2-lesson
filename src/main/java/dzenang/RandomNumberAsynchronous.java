package dzenang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RandomNumberAsynchronous {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(16);
    List<Future<Integer>> futureTasks = new ArrayList<>(16);

    for (int i = 0; i < 16; i++) {
      // create Callable object
      RandomNumber randomNumber = new RandomNumber();
      Future future = executorService.submit(randomNumber);
      futureTasks.add(future);
      // we don't need to do anything related to threads
    }

    try {
      Thread.sleep(1000);
      printResults(futureTasks);
    } catch (InterruptedException e) {
      System.out.println("Thread is interrupted: " + e.getMessage());
    } catch (ExecutionException e) {
      System.out.println("Problem in thread execution: " + e.getMessage());
    }

    executorService.shutdown();
  }

  private static void printResults(List<Future<Integer>> futureTasks)
      throws ExecutionException, InterruptedException {
    for (Future<Integer> futureTask : futureTasks) {
      if (futureTask.isDone()) {
        Integer result = futureTask.get();
        System.out.println("The random number is " + result);
      } else {
        System.out.println("Result not ready yet.");
      }
    }
  }
}

package dzenang;

import java.util.Random;
import java.util.concurrent.Callable;

public class RandomNumber implements Callable<Integer> {

  // inheritance implementation of Callable interface
  @Override
  public Integer call() throws Exception {
    Random random = new Random();
    int number = random.nextInt(16);
    Thread.sleep(1000);
    return number;
  }
}

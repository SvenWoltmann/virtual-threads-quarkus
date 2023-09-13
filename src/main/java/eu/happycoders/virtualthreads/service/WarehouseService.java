package eu.happycoders.virtualthreads.service;

import static eu.happycoders.virtualthreads.service.SleepUtil.sleepApproximately;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class WarehouseService {

  // We're making this super-simple: a product is either available or not.
  // In reality there are many more shades:
  // - ships within X days
  // - multiple delivery options to customer's address with different prices
  // - ...
  public boolean isAvailable(String productId) {
    try {
      sleepApproximately(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return false;
    }

    return ThreadLocalRandom.current().nextBoolean();
  }

  public CompletableFuture<Boolean> isAvailableAsync(String productId) {
    return CompletableFuture.supplyAsync(() -> isAvailable(productId));
  }
}

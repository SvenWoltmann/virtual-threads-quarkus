package eu.happycoders.virtualthreads.service;

import static eu.happycoders.virtualthreads.service.SleepUtil.sleepApproximately;

import eu.happycoders.virtualthreads.model.Supplier;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class SupplierService {

  // We're making this super-simple: a product is either available or not.
  // In reality there are many more shades:
  // - ships within X days
  // - multiple delivery options to customer's address with different prices
  // - ...
  public int getDeliveryTime(Supplier supplier, String productId) {
    try {
      sleepApproximately(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return 0;
    }

    return ThreadLocalRandom.current().nextInt(1, 5);
  }

  public CompletableFuture<Integer> getDeliveryTimeAsync(Supplier supplier, String productId) {
    int deliveryTime = getDeliveryTime(supplier, productId);
    return CompletableFuture.completedFuture(deliveryTime);
  }
}

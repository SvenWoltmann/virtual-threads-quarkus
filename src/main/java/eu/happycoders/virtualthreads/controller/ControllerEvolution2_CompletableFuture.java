package eu.happycoders.virtualthreads.controller;

import eu.happycoders.virtualthreads.model.ProductPageResponse;
import eu.happycoders.virtualthreads.service.ProductService;
import eu.happycoders.virtualthreads.service.SupplierService;
import eu.happycoders.virtualthreads.service.WarehouseService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/stage2-cf")
@Produces(MediaType.APPLICATION_JSON)
public class ControllerEvolution2_CompletableFuture {

  private final ProductService productService;
  private final SupplierService supplierService;
  private final WarehouseService warehouseService;

  public ControllerEvolution2_CompletableFuture(
      ProductService productService,
      SupplierService supplierService,
      WarehouseService warehouseService) {
    this.productService = productService;
    this.supplierService = supplierService;
    this.warehouseService = warehouseService;
  }

  @GET
  @Path("/product/{productId}")
  public CompletionStage<Response> getProduct(@PathParam("productId") String productId) {
    return productService
        .getProductAsync(productId)
        .thenCompose(
            product -> {
              if (product.isEmpty()) {
                return CompletableFuture.completedFuture(Response.status(Status.NOT_FOUND).build());
              }

              return warehouseService
                  .isAvailableAsync(productId)
                  .thenCompose(
                      available ->
                          available
                              ? CompletableFuture.completedFuture(0)
                              : supplierService.getDeliveryTimeAsync(
                                  product.get().supplier(), productId))
                  .thenApply(
                      daysUntilShippable ->
                          Response.ok(
                                  new ProductPageResponse(
                                      product.get(),
                                      daysUntilShippable,
                                      Thread.currentThread().toString()))
                              .build());
            });
  }
}

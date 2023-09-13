package eu.happycoders.virtualthreads.controller;

import eu.happycoders.virtualthreads.model.Product;
import eu.happycoders.virtualthreads.model.ProductPageResponse;
import eu.happycoders.virtualthreads.service.ProductService;
import eu.happycoders.virtualthreads.service.SupplierService;
import eu.happycoders.virtualthreads.service.WarehouseService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/stage1-seq")
@Produces(MediaType.APPLICATION_JSON)
public class ControllerEvolution1_Sequential {

  private final ProductService productService;
  private final SupplierService supplierService;
  private final WarehouseService warehouseService;

  public ControllerEvolution1_Sequential(
      ProductService productService,
      SupplierService supplierService,
      WarehouseService warehouseService) {
    this.productService = productService;
    this.supplierService = supplierService;
    this.warehouseService = warehouseService;
  }

  @GET
  @Path("/product/{productId}")
  public ProductPageResponse getProduct(@PathParam("productId") String productId) {
    Product product = productService.getProduct(productId).orElseThrow(NotFoundException::new);

    boolean available = warehouseService.isAvailable(productId);

    int shipsInDays =
        available ? 0 : supplierService.getDeliveryTime(product.supplier(), productId);

    return new ProductPageResponse(product, shipsInDays, Thread.currentThread().toString());
  }
}

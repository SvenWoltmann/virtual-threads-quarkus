package eu.happycoders.virtualthreads.model;

public record ProductPageResponse(Product product, int daysUntilShippable, String thread) {}

package com.williamfeliciano.productservice.service;

import com.williamfeliciano.productservice.model.ProductRequest;
import com.williamfeliciano.productservice.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}

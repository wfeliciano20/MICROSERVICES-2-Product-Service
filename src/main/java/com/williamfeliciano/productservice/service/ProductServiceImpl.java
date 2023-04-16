package com.williamfeliciano.productservice.service;

import com.williamfeliciano.productservice.entity.Product;
import com.williamfeliciano.productservice.exception.ProductServiceCustomException;
import com.williamfeliciano.productservice.model.ProductRequest;
import com.williamfeliciano.productservice.model.ProductResponse;
import com.williamfeliciano.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@AllArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product" + productRequest.getName());
        Product product =
                Product.builder()
                        .productName(productRequest.getName())
                        .price(productRequest.getPrice())
                        .quantity(productRequest.getQuantity())
                        .build();
        productRepository.save(product);
        log.info("Product created successfully");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId: {}", productId);
        Product product = productRepository
                .findById(productId)
                .orElseThrow(()-> new ProductServiceCustomException("Product with given id not found","404 Product Not Found"));
        ProductResponse productResponse = new ProductResponse();
        copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reducing queantity {} for Id: {}",quantity,productId);
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(()-> new ProductServiceCustomException("Product with given id not found","404 Product not found"));
        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity.","400 Bad Request- Insufficient Quantity");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity Updated Successfully");

    }
}

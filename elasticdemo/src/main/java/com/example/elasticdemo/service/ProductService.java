package com.example.elasticdemo.service;

import com.example.elasticdemo.model.Product;
import com.example.elasticdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        Page<Product> pageResult = productRepository.findAll(PageRequest.of(0, 10)); // 10 items per page
        return pageResult.getContent(); // Extract List<Product>
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(String id, Product updatedProduct) {
        Optional<Product> existingProductOpt = productRepository.findById(id);

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Update fields (if not null)
            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getPrice() > 0) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }

            return productRepository.save(existingProduct); // Save updated product
        } else {
            throw new RuntimeException("Product with ID " + id + " not found.");
        }
    }
}

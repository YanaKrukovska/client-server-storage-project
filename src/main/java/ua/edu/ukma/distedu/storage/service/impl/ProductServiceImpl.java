package ua.edu.ukma.distedu.storage.service.impl;

import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.repository.ProductRepository;
import ua.edu.ukma.distedu.storage.service.ProductService;

public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}

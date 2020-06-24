package ua.edu.ukma.distedu.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.model.Response;
import ua.edu.ukma.distedu.storage.persistence.repository.ProductRepository;
import ua.edu.ukma.distedu.storage.service.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Response<Product> save(Product product) {
        Product productDB = productRepository.findProductByName(product.getName());

        if (productDB != null) {
            return new Response<>(product, new LinkedList<>(Collections.singletonList("Name of the product must be unique")));
        }

        return new Response<>(productRepository.save(product), new LinkedList<>());
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void delete(List<Product> productList) {
        productRepository.deleteAll(productList);
    }

    @Override
    public Product findProductById(long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public List<Product> findAllByGroup(Group group) {
        return productRepository.findAllByGroup(group);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public double findOverallCost(Product product) {
        return product.getAmount() * product.getPrice();
    }

    @Override
    public void update(Product product) {
        Product productDB = productRepository.findProductById(product.getId());
        productDB.setName(product.getName());
        productDB.setGroup(product.getGroup());
        productDB.setPrice(product.getPrice());
        productDB.setAmount(product.getAmount());
        productDB.setProducer(product.getProducer());
        productDB.setDescription(product.getDescription());
        productRepository.save(productDB);
    }


}

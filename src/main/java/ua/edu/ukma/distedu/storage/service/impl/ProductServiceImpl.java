package ua.edu.ukma.distedu.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.repository.ProductRepository;
import ua.edu.ukma.distedu.storage.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
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
    public void update(Product product) {
        Product productDB = productRepository.findProductById(product.getId());
        System.err.println(productDB);
        productDB.setName(product.getName());
        productDB.setGroup(product.getGroup());
        productDB.setPrice(product.getPrice());
        productDB.setAmount(product.getAmount());
        productDB.setProducer(product.getProducer());
        productDB.setDescription(product.getDescription());
        productRepository.save(productDB);
    }


}

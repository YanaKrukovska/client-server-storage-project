package ua.edu.ukma.distedu.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.repository.ProductRepository;
import ua.edu.ukma.distedu.storage.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

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
    public List<Product> findByName(String name) {
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Product> example = Example.of(
                new Product(name,null,"",0,0,""),
                matcher);
//        List<Product> found = productRepository.findAll(example);
        return productRepository.findAll(example);
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

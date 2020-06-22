package ua.edu.ukma.distedu.storage.service;

import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    void delete(Product product);

    void delete(List<Product> productList);

    void update(Product product);

    Product findProductById(long id);

    List<Product> findAllByGroup(Group group);

    List<Product> findAll();

    double findOverallCost(Product product);

}

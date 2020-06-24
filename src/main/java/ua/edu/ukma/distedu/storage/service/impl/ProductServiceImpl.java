package ua.edu.ukma.distedu.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.model.Response;
import ua.edu.ukma.distedu.storage.persistence.repository.ProductRepository;
import ua.edu.ukma.distedu.storage.service.ProductService;

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
        List<String> errors = validateProduct(product);
        if (errors.size() != 0) {
            return new Response<>(product, errors);
        }

        if (productRepository.findProductByName(product.getName()) != null) {
            errors.add("Name of the product must be unique");
            return new Response<>(product, errors);
        }

        return new Response<>(productRepository.save(product), new LinkedList<>());
    }

    @Override
    public List<String> validateProduct(Product product) {

        List<String> errors = new LinkedList<>();

        if (product.getName().equals("")) {
            errors.add("Name cannot be empty");
        }
        if (product.getAmount() < 0) {
            errors.add("Amount cannot be < 0");
        }
        if (product.getPrice() < 0) {
            errors.add("Price cannot be < 0");
        }
        if (product.getGroup() == null || product.getGroup().getId() == 0) {
            errors.add("Select group");
        }

        return errors;
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
    public Response<Product> update(Product product) {

        List<String> errors = validateProduct(product);
        if (errors.size() != 0) {
            return new Response<>(product, errors);
        }

        Product sameNameProduct = productRepository.findProductByName(product.getName());
        if (sameNameProduct != null && sameNameProduct.getId() != product.getId()) {
            errors.add("Product with such name already exists");
            return new Response<>(product, errors);
        }

        Product productDB = productRepository.findProductById(product.getId());
        productDB.setName(product.getName());
        productDB.setGroup(product.getGroup());
        productDB.setPrice(product.getPrice());
        productDB.setAmount(product.getAmount());
        productDB.setProducer(product.getProducer());
        productDB.setDescription(product.getDescription());
        return new Response<>(productRepository.save(productDB), errors);

    }


}

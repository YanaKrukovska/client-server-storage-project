package ua.edu.ukma.distedu.storage.service;

import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.model.Response;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Response<Product> save(Product product);

    List<String> validateProduct(Product product);

    void delete(Product product);

    void delete(List<Product> productList);

    Response<Product> update(Product product);

    Product findProductById(long id);

    List<Product> findProductByIdAsList(long id);

    List<Product> findAllByGroup(Group group);

    List<Product> findAllByGroupId(long groupId);

    List<Product> findAll();

    double findOverallCost(Product product);

    List<Product> findAllByNameSnippet(String snippet);

    List<Product> findAllByNameSnippetAndGroup(String snippet, long groupId);

    List<Product> findProductByIdAndGroup(long id, long groupId);

    List<Product> searchProduct(long groupId, long productId, String nameSnippet);

    BigDecimal findSumFotList(List<Product> products);

    Response<Product> addProductAmount(long id, long amount);
}

package ua.edu.ukma.distedu.storage.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(long id);

    Product findProductByName(String name);

    List<Product> findAllByGroup(Group group);

    List<Product> findAllByGroup_Id(long groupId);

    List<Product> findProductsByNameContainsIgnoreCase(String snippet);

    List<Product> findProductByNameContainsIgnoreCaseAndGroup_Id(String snippet, long groupId);

    List<Product> findProductByIdAndGroup_Id(long id, long groupId);

    List<Product> findAllById(long id);

    @Query(value = "select sum(p.amount * p.price)from products p where p.id in ?1", nativeQuery = true)
    BigDecimal sumForList(List<Long> productIds);

}

package ua.edu.ukma.distedu.storage.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(long id);

    List<Product> findAllByGroup(Group group);
}

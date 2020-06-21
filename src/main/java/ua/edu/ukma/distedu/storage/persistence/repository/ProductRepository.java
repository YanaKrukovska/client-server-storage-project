package ua.edu.ukma.distedu.storage.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.storage.persistence.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

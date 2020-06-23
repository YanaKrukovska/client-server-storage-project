package ua.edu.ukma.distedu.storage.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.storage.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);

}

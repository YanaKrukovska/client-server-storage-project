package ua.edu.ukma.distedu.storage.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.storage.persistence.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findGroupById(long id);
}

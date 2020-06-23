package ua.edu.ukma.distedu.storage.service;

import ua.edu.ukma.distedu.storage.persistence.model.Group;

import java.util.List;

public interface GroupService {

    Group save(Group group);

    void delete(Group group);

    Group findGroupById(long id);

    void update(Group group);

    double findOverallCost(Group group);
    List<Group> findAll();

}

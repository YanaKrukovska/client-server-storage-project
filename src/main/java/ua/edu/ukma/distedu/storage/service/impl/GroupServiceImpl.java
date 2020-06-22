package ua.edu.ukma.distedu.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.model.Product;
import ua.edu.ukma.distedu.storage.persistence.repository.GroupRepository;
import ua.edu.ukma.distedu.storage.service.GroupService;
import ua.edu.ukma.distedu.storage.service.ProductService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ProductService productService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ProductService productService) {
        this.groupRepository = groupRepository;
        this.productService = productService;
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(Group group) {
        productService.delete(productService.findAllByGroup(group));
        groupRepository.delete(group);
    }

    @Override
    public Group findGroupById(long id) {
        return groupRepository.findGroupById(id);
    }

    @Override
    public void update(Group group) {
        Group groupDB = groupRepository.findGroupById(group.getId());
        groupDB.setName(group.getName());
        groupDB.setDescription(group.getDescription());
        groupRepository.save(groupDB);
    }

    @Override
    public double findOverallCost(Group group) {

        List<Product> products = productService.findAllByGroup(group);
        double cost = 0;
        for (Product product : products) {
            cost += productService.findOverallCost(product);
        }

        return cost;
    }
}

package ua.edu.ukma.distedu.storage.service.impl;

import ua.edu.ukma.distedu.storage.persistence.model.Group;
import ua.edu.ukma.distedu.storage.persistence.repository.GroupRepository;
import ua.edu.ukma.distedu.storage.service.GroupService;

public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }
}

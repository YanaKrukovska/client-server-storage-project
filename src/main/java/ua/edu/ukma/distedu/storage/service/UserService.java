package ua.edu.ukma.distedu.storage.service;

import ua.edu.ukma.distedu.storage.persistence.model.Response;
import ua.edu.ukma.distedu.storage.persistence.model.User;

import java.util.List;

public interface UserService {

    Response<User> addUser(User user);

    List<String> validateUser(User user);

    User findUserById(long id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    List<User> getAllUsers();
 }

package ua.edu.ukma.distedu.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.storage.persistence.model.Response;
import ua.edu.ukma.distedu.storage.persistence.model.Role;
import ua.edu.ukma.distedu.storage.persistence.model.User;
import ua.edu.ukma.distedu.storage.persistence.repository.UserRepository;
import ua.edu.ukma.distedu.storage.service.PasswordService;
import ua.edu.ukma.distedu.storage.service.UserService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public Response<User> addUser(User user) {

        List<String> errors = validateUser(user);

        if (errors.size() != 0) {
            return new Response<>(user, errors);
        }

        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            errors.add("User with such username already exists");
            return new Response<>(user, errors);
        }

        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            errors.add("User with such email already exists");
            return new Response<>(user, errors);
        }

        if (!passwordService.comparePasswordAndConfirmationPassword(user.getPassword(), user.getPasswordConfirm())) {
            errors.add("Passwords are different");
            return new Response<>(user, errors);
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        user.setPassword(passwordService.encodePassword(user.getPassword()));
        return new Response<>(userRepository.save(user), new LinkedList<>());
    }

    @Override
    public List<String> validateUser(User user) {

        List<String> errors = new LinkedList<>();
        if (user.getUsername().equals("")) {
            errors.add("Username can't be empty");
        }

        if (user.getEmail().equals("")) {
            errors.add("Email can't be empty");
        }

        if (user.getPassword().equals("")) {
            errors.add("Password can't be empty");
        }

        return errors;
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}

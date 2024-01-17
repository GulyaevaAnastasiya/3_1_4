package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository,
                           @Lazy PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void add(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EntityExistsException(String.format("User with name: %s is already exist", user.getName()));
        } else {
            createRolesIfNotExist();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Transactional
    public void createRolesIfNotExist() {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role(1L, "ROLE_USER"));
        }
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        }
    }

    @Override
    public List<User> usersList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Long id, User user) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException(String.format("User with id: %s not found", id)));
        if (currentUser.getUsername().equals(user.getUsername()) && !currentUser.getId().equals(id)) {
            throw new EntityExistsException(String.format("User with name: %s is already exist", user.getName()));
        }
        if (user.getPassword().isBlank() && !user.getPassword().equals(currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setRoles(user.getRoles());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException
                ("User with id " + id + " not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with name %s not found", username)));
    }
}

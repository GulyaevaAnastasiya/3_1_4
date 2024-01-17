package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role role);

    Collection<Role> findAll();

    Optional<Role> findById(Long id);

    void deleteById(Long id);

    Optional<Role> findByName(String name);
}

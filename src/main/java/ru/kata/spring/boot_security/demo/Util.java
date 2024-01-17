//package ru.kata.spring.boot_security.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.entity.Role;
//import ru.kata.spring.boot_security.demo.entity.User;
//import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import javax.annotation.PostConstruct;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//@Component
//public class Util {
//    private final RoleService roleService;
//    private final UserService userService;
//
//    @Autowired
//    public Util(RoleService roleService, UserService userService) {
//        this.roleService = roleService;
//        this.userService = userService;
//    }
//
//    @PostConstruct
//    public void initTestUsers() {
//        userService.createRolesIfNotExist();
//        userService.add(new User("user", "user@mail.ru","8911111111", "user", roleService.findById(1L).map(Collections::singletonList).orElseGet(Collections::emptyList)));
//        userService.add(new User("admin", "admin@mail.ru","8911511111", "admin", roleService.findById(2L).map(Collections::singletonList).orElseGet(Collections::emptyList)));
//
//    }
//}

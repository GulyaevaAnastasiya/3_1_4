package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminRestController {
    private final RoleService roleService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminRestController(RoleService roleService, UserServiceImpl userServiceImpl) {
        this.roleService = roleService;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public String allUsers(Model model) {
        List<User> users = userServiceImpl.usersList();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/add")
    public String createUserForm(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = (List<Role>) roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "add";
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             Model model) {
        userServiceImpl.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-update")
    public String showFormForUpdate(@RequestParam("id") long id,
                                    Model model) {
        User user = userServiceImpl.getUser(id);
        model.addAttribute("user", user);
        List<Role> roles = (List<Role>) roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") @Valid User user, Model model) {
        userServiceImpl.update(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping("/user-delete")
    public String delete(@RequestParam("id") long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}

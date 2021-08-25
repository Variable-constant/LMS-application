package com.karpenko.lms.controller;

import com.karpenko.lms.controller.exceptions.NotFoundException;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.Role;
import com.karpenko.lms.dto.UserDTO;
import com.karpenko.lms.service.RoleService;
import com.karpenko.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam(name = "titlePrefix", required = false) String titlePrefix,
                              Principal principal) {
        model.addAttribute("users", userService.findByNameWithPrefix(titlePrefix == null ? "" : titlePrefix));
        model.addAttribute("activePage", "users");
        return "users_list";
    }

    @GetMapping("/{id}")
    public String userForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "user_information";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user_information";
    }

    @PostMapping
    public String submitUserForm(@Valid @ModelAttribute("user") UserDTO user,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile_change";
        }

        userService.save(user);
        return "redirect:/admin/user";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user";
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }
}

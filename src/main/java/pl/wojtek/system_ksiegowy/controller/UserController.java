package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wojtek.system_ksiegowy.model.SystemUserRole;
import pl.wojtek.system_ksiegowy.repository.UserRoleRepository;
import pl.wojtek.system_ksiegowy.service.UserService;
import pl.wojtek.system_ksiegowy.model.SystemUser;
import pl.wojtek.system_ksiegowy.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Controller
public class UserController
{
    private UserService userService;

    private UserRepository userRepository;

    private UserRoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {this.roleRepository = roleRepository;}

    @GetMapping("/users/add")
    public String addUser(Model model)
    {
        model.addAttribute("user", new SystemUser());
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute SystemUser user, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
            return "addUser";
        else
         {
            model.addAttribute("userAdded", "Użytkownik dodany");
                user.setAccountNonExpired(true);
                user.setCredentialsNonExpired(true);
                user.setAccountNonLocked(true);
                userService.addWithDefaultRole(user);
                return "loggedHome";
         }
    }

    @RequestMapping("/users/list")
    public String usersList(Model model)
    {
        List<SystemUser> users = new ArrayList<>();
        users = userRepository.findAll();
        model.addAttribute("users", users);
        return "listUser";
    }

    @GetMapping("/users/list/user={id}")
    public String getUser(@PathVariable (value = "id") Long id, Model model)
    {
        SystemUser user = userRepository.getById(id);
        List<SystemUserRole> allRoles = roleRepository.findAll();
        Set<SystemUserRole> userRoles = new HashSet<>(allRoles);
        for (SystemUserRole allRole : allRoles)
        {
            if (user.getRoles().isEmpty())
                userRoles.add(allRole);
            else
            {
                for (SystemUserRole role1 : user.getRoles())
                {
                    if (role1.getRoleName().equals(allRole.getRoleName()))
                        userRoles.remove(role1);
                }
            }
        }
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("user", user);

        return "userOverview";
    }

    @PostMapping("/users/list/user={id}")
    public String updateUser(@PathVariable (value = "id") Long id, Model model, @ModelAttribute SystemUser user, @RequestParam(required = false, name = "permissions") List<String> permissions)
    {
        if (permissions == null)
        {
            user.getRoles().clear();
            userRepository.save(user);
        }
        else
        {
            if (permissions.size() == 1) {
                SystemUserRole role = roleRepository.findByRoleName(permissions.get(0));
                user.getRoles().clear();
                user.getRoles().add(role);
                userRepository.save(user);
            } else if (permissions.size() > 1)
            {
                Set<SystemUserRole> roles = new HashSet<SystemUserRole>();
                for (String s : permissions)
                {
                    SystemUserRole role = roleRepository.findByRoleName(s);
                    roles.add(role);
                }

                user.getRoles().clear();
                user.setRoles(roles);
                userRepository.save(user);
            }
        }
        List<SystemUserRole> allRoles = roleRepository.findAll();
        Set<SystemUserRole> userRoles = new HashSet<>(allRoles);
        for (SystemUserRole allRole : allRoles)
        {
            if (user.getRoles().isEmpty())
                userRoles.add(allRole);
            else
            {
                for (SystemUserRole role1 : user.getRoles())
                {
                    if (role1.getRoleName().equals(allRole.getRoleName()))
                        userRoles.remove(role1);
                }
            }
        }
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("user", user);
        return "userOverview";
    }

    private Set<SystemUserRole> convert(String convert)
    {
        List<String> findRoles = new ArrayList<>();
        Set<SystemUserRole> roles = new HashSet<>();
        System.out.println(convert + 2);
        StringTokenizer tokenizer = new StringTokenizer(convert, ",");
        while (tokenizer.hasMoreTokens())
        {
            findRoles.add(tokenizer.nextToken());
        }
        for (String s : findRoles)
        {
            SystemUserRole sysRole = roleRepository.findByRoleName(s);
            roles.add(sysRole);
            System.out.println(sysRole + "rola");
        }
        return roles;
    }
}

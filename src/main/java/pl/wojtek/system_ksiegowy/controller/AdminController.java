package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wojtek.system_ksiegowy.model.Company;
import pl.wojtek.system_ksiegowy.model.SystemUserRole;
import pl.wojtek.system_ksiegowy.repository.CompanyRepository;
import pl.wojtek.system_ksiegowy.repository.UserRoleRepository;
import pl.wojtek.system_ksiegowy.model.SystemUser;
import pl.wojtek.system_ksiegowy.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController
{
    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    private UserRepository userRepository;

    private UserRoleRepository roleRepository;

    private CompanyRepository companyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {this.companyRepository = companyRepository;}

    @GetMapping("/admin/users/add")
    public String addUser(Model model)
    {
        List<SystemUserRole> allRoles = roleRepository.findAll();
        model.addAttribute("userRoles", allRoles);
        model.addAttribute("user", new SystemUser());
        return "addUser";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@ModelAttribute SystemUser user, BindingResult bindingResult, Model model, @RequestParam(required = false, name = "permissions") List<String> permissions) throws Exception
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        user.setAddDate(dateFormat.parse(dateFormat.format(date)));
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);

        if (bindingResult.hasErrors())
            return "addUser";
        else
        {
            if (permissions == null) {
                user.getRoles().clear();
                userRepository.save(user);
            } else
                {
                if (permissions.size() == 1) {
                    SystemUserRole role = roleRepository.findByRoleName(permissions.get(0));
                    user.getRoles().clear();
                    user.getRoles().add(role);
                    userRepository.save(user);
                } else if (permissions.size() > 1) {
                    Set<SystemUserRole> roles = new HashSet<SystemUserRole>();
                    for (String s : permissions) {
                        SystemUserRole role = roleRepository.findByRoleName(s);
                        roles.add(role);
                    }

                    user.getRoles().clear();
                    user.setRoles(roles);
                    userRepository.save(user);
                }
            }

            return "redirect:/admin/users/list";
        }
    }

    @RequestMapping("/admin/users/list")
    public String usersList(Model model) {
        List<SystemUser> users = new ArrayList<>();
        users = userRepository.findAll();
        model.addAttribute("users", users);
        return "listUser";
    }

    @GetMapping("/admin/users/list/user={id}")
    public String getUser(@PathVariable(value = "id") Long id, Model model)
    {
        SystemUser user = userRepository.getById(id);
        List<SystemUserRole> allRoles = roleRepository.findAll();
        TreeSet<SystemUserRole> userRoles = new TreeSet<>(allRoles);
        for (SystemUserRole allRole : allRoles) {
            if (user.getRoles().isEmpty())
                userRoles.add(allRole);
            else {
                for (SystemUserRole role1 : user.getRoles()) {
                    if (role1.getRoleName().equals(allRole.getRoleName()))
                        userRoles.remove(role1);
                }
            }
        }
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("user", user);

        return "userOverview";
    }

    @PostMapping("/admin/users/list/user={id}")
    public String updateUser(@PathVariable(value = "id") Long id, Model model, @ModelAttribute SystemUser user, @RequestParam(required = false, name = "permissions") List<String> permissions)
    {
        if (permissions == null) {
            user.getRoles().clear();
            userRepository.save(user);
        } else {
            if (permissions.size() == 1) {
                SystemUserRole role = roleRepository.findByRoleName(permissions.get(0));
                user.getRoles().clear();
                user.getRoles().add(role);
                userRepository.save(user);
            } else if (permissions.size() > 1) {
                Set<SystemUserRole> roles = new HashSet<SystemUserRole>();
                for (String s : permissions) {
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
        for (SystemUserRole allRole : allRoles) {
            if (user.getRoles().isEmpty())
                userRoles.add(allRole);
            else {
                for (SystemUserRole role1 : user.getRoles()) {
                    if (role1.getRoleName().equals(allRole.getRoleName()))
                        userRoles.remove(role1);
                }
            }
        }
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("user", user);
        return "userOverview";
    }


    @GetMapping("/admin/company_data")
    public String getCompanyData(Model model)
    {
        Company company = companyRepository.getById(1l);
        model.addAttribute("company", company);
        return "companyData";
    }

    @PostMapping("/admin/company_data")
    public String getCompanyData(Model model, @ModelAttribute Company company)
    {
        companyRepository.save(company);
        Company ownCompany = companyRepository.getById(1l);
        model.addAttribute("company", ownCompany);
        return "companyData";
    }

    @RequestMapping("/admin/logged")
    public String listLoggedInUsers(Model model)
    {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<String> usersLogin = new ArrayList<>();
        List<SystemUser> systemUsers = new ArrayList<>();

        for(Object principal : allPrincipals)
        {
            if(principal instanceof User)
            {
                User user = (User) principal;
                usersLogin.add(user.getUsername());
            }
        }
        for (String login : usersLogin)
            systemUsers.add(userRepository.findByLogin(login));

        model.addAttribute("loggedUsers", systemUsers);
        return "loggedUsers";
    }
}
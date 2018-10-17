package pl.wojtek.system_ksiegowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wojtek.system_ksiegowy.model.SystemUser;
import pl.wojtek.system_ksiegowy.model.SystemUserRole;
import pl.wojtek.system_ksiegowy.repository.UserRepository;
import pl.wojtek.system_ksiegowy.repository.UserRoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.Calendar;


@Service
public class UserService
{
    private static final String DEFAULT_ROLE = "ROLE_USER";

    private UserRepository userRepository;

    private UserRoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public void addWithDefaultRole(SystemUser user)
    {

        SystemUserRole defaultRole = roleRepository.findByRoleName(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        user.setAddDate(new Date(Calendar.getInstance().getTime().getTime()));
        userRepository.save(user);
    }
}

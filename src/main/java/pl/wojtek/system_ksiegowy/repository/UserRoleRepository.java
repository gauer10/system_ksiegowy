package pl.wojtek.system_ksiegowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojtek.system_ksiegowy.model.SystemUser;
import pl.wojtek.system_ksiegowy.model.SystemUserRole;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<SystemUserRole, Long>
{
    SystemUserRole findByRoleName(String role);
}

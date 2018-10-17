package pl.wojtek.system_ksiegowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojtek.system_ksiegowy.model.SystemUser;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, Long>
{
    SystemUser findByLogin(String login);
    SystemUser getById(Long id);
}

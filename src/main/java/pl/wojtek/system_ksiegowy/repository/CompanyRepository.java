package pl.wojtek.system_ksiegowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojtek.system_ksiegowy.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>
{
    Company getById(Long id);
}

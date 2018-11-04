package pl.wojtek.system_ksiegowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojtek.system_ksiegowy.model.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>
{
    Contractor getById(Long id);
}

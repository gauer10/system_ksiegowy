package pl.wojtek.system_ksiegowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojtek.system_ksiegowy.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>
{
    Contract getById(Long id);
}

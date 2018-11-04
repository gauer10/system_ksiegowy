package pl.wojtek.system_ksiegowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojtek.system_ksiegowy.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>
{
    Invoice getById(Long id);
}

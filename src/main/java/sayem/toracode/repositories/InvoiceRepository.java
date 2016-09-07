package sayem.toracode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sayem.toracode.entities.BusinessPartnerEntity;
import sayem.toracode.entities.InvoiceEntity;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

	List<InvoiceEntity> findByBusinessPartner(BusinessPartnerEntity partner);

}

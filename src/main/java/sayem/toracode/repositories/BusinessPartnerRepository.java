package sayem.toracode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sayem.toracode.entities.BusinessPartnerEntity;

@Repository
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartnerEntity, Long> {
	public BusinessPartnerEntity findById(Long id);
	public List<BusinessPartnerEntity> findByType(String type);
}

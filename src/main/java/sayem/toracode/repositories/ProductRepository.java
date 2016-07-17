package sayem.toracode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sayem.toracode.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	List<ProductEntity> findByType(String type);
	List<ProductEntity> findBySerial(String serial);
}

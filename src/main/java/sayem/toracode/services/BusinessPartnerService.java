package sayem.toracode.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sayem.toracode.entities.BusinessPartnerEntity;
import sayem.toracode.repositories.BusinessPartnerRepository;

@Service
public class BusinessPartnerService {
	
	@Autowired
	private BusinessPartnerRepository businessPartnerRepository;
	
	public BusinessPartnerEntity save(BusinessPartnerEntity partner) throws IOException{
		return businessPartnerRepository.save(partner);
	}
	
	public List<BusinessPartnerEntity> findAll(){
		return businessPartnerRepository.findAll();
	}
	
	public List<BusinessPartnerEntity> findAllCustomers(){
		return businessPartnerRepository.findByType(BusinessPartnerEntity.PARTNER_TYPE_CUSTOMER);
	}
	
	public List<BusinessPartnerEntity> findAllSuppliers(){
		return businessPartnerRepository.findByType(BusinessPartnerEntity.PARTNER_TYPE_SUPPLIER);
	}
	
	public BusinessPartnerEntity findById(Long id){
		return businessPartnerRepository.findById(id);
	}

	public BusinessPartnerEntity findByName(String name) {
		return businessPartnerRepository.findByName(name);
	}
	
}

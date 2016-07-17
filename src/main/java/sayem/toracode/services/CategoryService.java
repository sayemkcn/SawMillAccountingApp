package sayem.toracode.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sayem.toracode.entities.CategoryEntity;
import sayem.toracode.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryEntity> findAll(){
		return categoryRepository.findAll();
	}
	
	public CategoryEntity findByName(String name){
		return categoryRepository.findByName(name);
	}
	
	public void saveCategory(CategoryEntity categoryEntity){
		categoryRepository.saveAndFlush(categoryEntity);
	}
	
}

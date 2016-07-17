package sayem.toracode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sayem.toracode.entities.CategoryEntity;
import sayem.toracode.services.CategoryService;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String addNewCategoryPage() {
		return "category/addCategory";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String addNewCategory(@ModelAttribute CategoryEntity categoryEntity, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", bindingResult.toString());
			return "category/addCategory";
		}
		categoryService.saveCategory(categoryEntity);
		model.addAttribute("message", "Successfully added category " + categoryEntity.getName());
		return "category/addCategory";
	}
}

package sayem.toracode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sayem.toracode.entities.ProductEntity;
import sayem.toracode.repositories.ProductRepository;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	private String showProducts(Model model) {
		model.addAttribute("productList", productRepository.findAll());
		return "product/viewAll";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private String showSingleProduct(@PathVariable("id") Long id, Model model) {
		ProductEntity productEntity = productRepository.findOne(id);
		model.addAttribute("product", productEntity);
		return "product/view";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProductForm() {
		return "product/addProduct";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute ProductEntity productEntity, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.toString());
		} else {
			double productSize = productEntity.getProductProperties().getSize(productEntity.getType());
			productEntity.setPurchasePrice((long) (productSize*productEntity.getProductProperties().getRate()));
			productRepository.save(productEntity);
			model.addAttribute("message", "Successfully saved!");
		}
		return "product/addProduct";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateProductForm(@PathVariable("id") Long id, Model model) {
		ProductEntity productEntity = productRepository.findOne(id);
		model.addAttribute("product", productEntity);
		return "product/addProduct";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute ProductEntity productEntity, BindingResult bindingResult,
			@PathVariable("id") Long id) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.toString());
		} else {
			productEntity.setId(id);
			productRepository.save(productEntity);
		}
		return "redirect:/product/" + id + "?message=Product successfully updated!";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") Long id) {
		try {
			productRepository.delete(id);
			return "redirect:/product?message=Successfully deleted!";
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "redirect:/product";
	}

}

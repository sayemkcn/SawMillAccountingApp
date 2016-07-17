package sayem.toracode.controllers;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sayem.toracode.entities.ProductEntity;
import sayem.toracode.repositories.InvoiceRepository;
import sayem.toracode.repositories.ProductRepository;
import sayem.toracode.services.ProductService;

@Controller
@RequestMapping(value = "/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ProductService productService;

	private static final String SESSION_ATTRIBUTE = "productList";
	private static final String SESSION_ATTRIBUTE_EDITED_ITEMS = "editedProductList";

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showAll(Model model) {
		model.addAttribute("invoiceList", invoiceRepository.findAll());
		return "invoice/viewAll";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createInvoicePage(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "serial", required = false) String serialNumber, Model model) {
		if ((type == null || type.isEmpty()) && (serialNumber == null || serialNumber.isEmpty())) {
			model.addAttribute("productType", "Round");
			model.addAttribute("productList", productService.findAllProducts());
		} else if(!serialNumber.isEmpty()) {
			model.addAttribute("productType",type);
			model.addAttribute("productList",productService.findBySerial(serialNumber));
		}else{
			model.addAttribute("productType", type);
			model.addAttribute("productList", productService.findProductByType(type));
		}
		return "invoice/addInvoice";
	}

	// add selected item to session
	@RequestMapping(value = "/session/add/{id}", method = RequestMethod.GET)
	public String addProductToSession(@PathVariable("id") Long id, HttpSession session, String type, Model model) {
		// initialize page with product list
		if (type == null || type.isEmpty()) {
			model.addAttribute("productType", "Round");
			model.addAttribute("productList", productService.findAllProducts());
		} else {
			model.addAttribute("productType", type);
			model.addAttribute("productList", productService.findProductByType(type));
		}

		// add product to session
		ProductEntity product = productService.findById(id);
		List<ProductEntity> productList = (List<ProductEntity>) session
				.getAttribute(InvoiceController.SESSION_ATTRIBUTE);
		if (productList != null) {
			productList.add(product);
			session.setAttribute(InvoiceController.SESSION_ATTRIBUTE, productList);
		} else {
			productList = new ArrayList<>();
			productList.add(product);
			session.setAttribute(InvoiceController.SESSION_ATTRIBUTE, productList);
		}

		return "invoice/addInvoice";
	}

	@RequestMapping(value = "/session/remove/{id}", method = RequestMethod.GET)
	public String removeProductFromSession(@PathVariable("id") int id, HttpSession session, String type, Model model) {
		// initialize page with product list
		if (type == null || type.isEmpty()) {
			model.addAttribute("productType", "Round");
			model.addAttribute("productList", productService.findAllProducts());
		} else {
			model.addAttribute("productType", type);
			model.addAttribute("productList", productService.findProductByType(type));
		}

		// add product to session
		List<ProductEntity> productList = (List<ProductEntity>) session
				.getAttribute(InvoiceController.SESSION_ATTRIBUTE);
		try {
			for (ProductEntity product : productList) {
				if (product.getId() == id) {
					productList.remove(product);
				}
			}
		} catch (ConcurrentModificationException e) {
			// Fuck Off!
		}
		return "invoice/addInvoice";
	}

	@RequestMapping(value = "/session/edit/{id}", method = RequestMethod.GET)
	public String editSessionItemPage(@PathVariable("id") Long id, Model model) {
		model.addAttribute("product", productService.findById(id));
		return "invoice/editSessionItem";
	}

	@RequestMapping(value = "/session/edit/{id}", method = RequestMethod.POST)
	public String editSessionItem(@ModelAttribute ProductEntity product, @PathVariable("id") Long id, Model model,
			HttpSession session) {
		List<ProductEntity> productList = (List<ProductEntity>) session
				.getAttribute(InvoiceController.SESSION_ATTRIBUTE);

		// remove existing product from list
		try {
			for (ProductEntity prod : productList) {
				if (prod.getId() == id) {
					productList.remove(prod);
				}
			}
		} catch (ConcurrentModificationException e) {
			// Fuck off
		}

		// add edited product object to list
		product.setId(id);
		productList.add(product);

		// finally set session attribute
		session.setAttribute(InvoiceController.SESSION_ATTRIBUTE, productList);

		// store edited items to a separate session for final calculation with
		// existing product
		// for finding remaining product
		List<ProductEntity> editedProductItemList = new ArrayList<>();
		editedProductItemList.add(product);
		session.setAttribute(InvoiceController.SESSION_ATTRIBUTE_EDITED_ITEMS, editedProductItemList);

		return "redirect:/invoice/create";
	}

}

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

import sayem.toracode.entities.CategoryEntity;
import sayem.toracode.entities.InvoiceEntity;
import sayem.toracode.entities.ProductEntity;
import sayem.toracode.pojo.InvoiceProduct;
import sayem.toracode.repositories.InvoiceRepository;
import sayem.toracode.services.CategoryService;
import sayem.toracode.services.InvoiceService;
import sayem.toracode.services.ProductService;

@Controller
@RequestMapping(value = "/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private CategoryService categoryService;

	private static final String SESSION_ATTRIBUTE = "productList";

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showAll(Model model) {
		model.addAttribute("invoiceList", invoiceRepository.findAll());
		return "invoice/viewAll";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String showInvoice(@PathVariable("id") Long id,Model model){
		model.addAttribute("invoice",invoiceService.findById(id));
		return "invoice/view";
	}
	
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String editInvoice(@PathVariable("id") Long id,@RequestParam("status") String status){
		InvoiceEntity invoice = invoiceService.findById(id);
		invoice.setStatus(status);
		invoiceService.saveInvoice(invoice);
		return "redirect:/invoice/"+id+"?message=Invoice marked as "+status+"!";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createInvoicePage(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "serial", required = false) String serialNumber, Model model) {
		if ((type == null || type.isEmpty()) && (serialNumber == null || serialNumber.isEmpty())) {
			model.addAttribute("productType", "Round");
			model.addAttribute("productList", productService.findAllProducts());
		} else if (!serialNumber.isEmpty()) {
			model.addAttribute("productType", type);
			model.addAttribute("productList", productService.findBySerial(serialNumber));
		} else {
			model.addAttribute("productType", type);
			model.addAttribute("productList", productService.findProductByType(type));
		}
		return "invoice/addInvoice";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createInvoice(@RequestParam("discount") long discount, HttpSession session, Model model) {
		List<ProductEntity> sellingProductList = (List<ProductEntity>) session
				.getAttribute(InvoiceController.SESSION_ATTRIBUTE);
		if (sellingProductList == null || sellingProductList.isEmpty()) {
			return "redirect:/invoice/create?message=No product selected yet!";
		}
		// calculate the total selling price
		long sellPrice = invoiceService.calculatePrice(sellingProductList, discount);
		// find and save remaining product
		List<ProductEntity> remainingProductList = invoiceService.calculateRemainingProducts(sellingProductList);
		if (!remainingProductList.isEmpty()) {
			productService.saveProductList(remainingProductList);
		}
		// remove session items after transaction
		session.removeAttribute(SESSION_ATTRIBUTE);
		// create invoice
		InvoiceEntity invoice = new InvoiceEntity();
		invoice.setDiscount(discount);
		// copy selling product List<ProductEntity>  to List<Product> because Product is not entity and not the same object
		// as products are being sold partially
		List<InvoiceProduct> invoiceProductList = InvoiceProduct.copyList(sellingProductList);
		invoice.setProductList(invoiceProductList);
		invoice.setStatus("Pending..");
		// store returned invoice cause it has generated id
		invoice = invoiceService.saveInvoice(invoice);
		return "redirect:/invoice/"+invoice.getId()+"?message=" + "Successfully generated new invoice";
	}

	// add selected item to session
	@RequestMapping(value = "/session/add/{id}", method = RequestMethod.GET)
	public String addProductToSession(@PathVariable("id") Long id, HttpSession session, String type, Model model) {
		// just initialize page with product list
		// nothing to do with session
		if (type == null || type.isEmpty()) {
			model.addAttribute("productType", "Round");
			model.addAttribute("productList", productService.findAllProducts());
		} else {
			model.addAttribute("productType", type);
			model.addAttribute("productList", productService.findProductByType(type));
		}
		// end init

		// add product to session
		ProductEntity product = productService.findById(id);
		List<ProductEntity> productList = (List<ProductEntity>) session
				.getAttribute(InvoiceController.SESSION_ATTRIBUTE);

		if (productList == null) {
			productList = new ArrayList<>();
		}
		product.setSellPrice(productService.calculatePrice(product));
		productList.add(product);
		session.setAttribute(InvoiceController.SESSION_ATTRIBUTE, productList);

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
		model.addAttribute("categoryList", categoryService.findAll());
		return "invoice/editSessionItem";
	}

	@RequestMapping(value = "/session/edit/{id}", method = RequestMethod.POST)
	public String editSessionItem(@ModelAttribute ProductEntity product, @PathVariable("id") Long id, Model model,
			HttpSession session) {
		List<ProductEntity> productList = (List<ProductEntity>) session
				.getAttribute(InvoiceController.SESSION_ATTRIBUTE);

//		// remove existing product from list
//		try {
//			for (ProductEntity prod : productList) {
//				if (prod.getId() == id) {
//					productList.remove(prod);
//				}
//			}
//		} catch (ConcurrentModificationException e) {
//			// Fuck off
//		}

		// add edited product object to list
		product.setId(id);
		// find category with category name and save it to product object
		CategoryEntity category = categoryService.findByName(product.getCategoryName());
		product.setCategory(category);
		product.setSellPrice(productService.calculatePrice(product));
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getId()==id) {
				productList.set(i, product);
			}
		}

		// finally set session attribute
		session.setAttribute(InvoiceController.SESSION_ATTRIBUTE, productList);

		return "redirect:/invoice/create";
	}

}

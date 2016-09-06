package sayem.toracode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sayem.toracode.entities.BusinessPartnerEntity;
import sayem.toracode.services.BusinessPartnerService;
import sayem.toracode.services.ProductService;

@Controller
@RequestMapping("/partner")
public class BusinessPartnerController {
	@Autowired
	private BusinessPartnerService partnerService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllPartners(Model model) {
		model.addAttribute("partnerList", partnerService.findAll());
		return "partner/viewAll";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPartnerPage() {
		return "partner/createPartner";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPartner(@ModelAttribute BusinessPartnerEntity partner, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", bindingResult.toString());
			return "partner/createPartner";
		}
		try {
			partnerService.save(partner);
			return "redirect:/partner?message=Successfully created " + partner.getType() + "!";
		} catch (Exception e) {
			model.addAttribute("message", e.toString());
			return "partner/createPartner";
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String partnerProfile(@PathVariable Long id, Model model) {
		BusinessPartnerEntity partner = partnerService.findById(id);
		model.addAttribute("partner", partner);
		model.addAttribute("productList", productService.findByBusinessPartner(partner));
		return "partner/view";
	}

}

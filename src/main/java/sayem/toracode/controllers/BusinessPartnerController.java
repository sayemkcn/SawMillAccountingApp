package sayem.toracode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sayem.toracode.entities.BusinessPartnerEntity;
import sayem.toracode.services.BusinessPartnerService;

@Controller
@RequestMapping("/partner")
public class BusinessPartnerController {
	@Autowired
	private BusinessPartnerService partnerService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPartnerPage() {
		return "partner/createPartner";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPartner(@ModelAttribute BusinessPartnerEntity partner, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", bindingResult.toString());
			return "partner/createPartner";
		}
		try {
			partnerService.save(partner);
			return "redirect:/partner?message=Successfully created partner!";
		} catch (Exception e) {
			model.addAttribute("message",e.toString());
			return "partner/createPartner";
		}
	}

}

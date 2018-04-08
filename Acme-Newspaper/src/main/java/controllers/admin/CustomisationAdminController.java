package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Customisation;

import services.CustomisationService;


@Controller
@RequestMapping("/customisation/admin")
public class CustomisationAdminController {

	@Autowired
	CustomisationService customisationService;
	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(){
		ModelAndView result;
		Customisation customisation;
		
		
		customisation = this.customisationService.findCustomisation();
		
		result = new ModelAndView("customisation/display");
		result.addObject("customisation", customisation);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Customisation customisation;
		
		customisation = this.customisationService.findCustomisation();
		
		result = this.createEditModelAndView(customisation);
		
		
		return result;	
	}

	private ModelAndView createEditModelAndView(Customisation customisation) {
		ModelAndView result;
		
		
		result = new ModelAndView("customisation/edit");
		result.addObject("customisation", customisation);
		
		return result;
	}
	
	
	
}

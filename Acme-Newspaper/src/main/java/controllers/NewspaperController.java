
package controllers;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Newspaper;

import services.NewspaperService;


@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors

	public NewspaperController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		final String uri = "";
		newspapers = this.newspaperService.publishedNewspapers();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("uri", uri);
		return result;
	}	
	
	

}

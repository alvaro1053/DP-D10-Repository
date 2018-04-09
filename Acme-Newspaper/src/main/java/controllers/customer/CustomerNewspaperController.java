
package controllers.customer;

import java.util.Collection;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Customer;
import domain.Newspaper;

import services.CustomerService;
import services.NewspaperService;

@Controller
@RequestMapping("/newspaper/customer")
public class CustomerNewspaperController extends AbstractController{

	// Services

	@Autowired
	private NewspaperService	newspaperService;
	
	@Autowired
	private CustomerService	customerService;


	// Constructors

	public CustomerNewspaperController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String filter) {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		final Customer principal = this.customerService.findByPrincipal();
		final String uri = "/customer";
		newspapers = this.newspaperService.findByFilter(filter);
		
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("principal",principal);
		result.addObject("uri", uri);
		return result;
	}	

}


package controllers.user;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Newspaper;
import domain.User;

import services.NewspaperService;
import services.UserService;


@Controller
@RequestMapping("/newspaper/user")
public class UserNewspaperController extends AbstractController{

	// Services

	@Autowired
	private NewspaperService	newspaperService;
	
	@Autowired
	private UserService	userService;


	// Constructors

	public UserNewspaperController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		final User principal = this.userService.findByPrincipal();
		final String uri = "/user";
		newspapers = this.newspaperService.publishedNewspapers();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject(principal);
		result.addObject("uri", uri);
		return result;
	}	
	
	

}

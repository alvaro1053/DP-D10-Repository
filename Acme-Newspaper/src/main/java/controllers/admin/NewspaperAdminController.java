
package controllers.admin;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;

import domain.Newspaper;


import services.NewspaperService;



@Controller
@RequestMapping("/newspaper/admin")
public class NewspaperAdminController extends AbstractController{

	// Services

	@Autowired
	private NewspaperService	newspaperService;



	// Constructors

	public NewspaperAdminController() {
		super();
	}
	
	
	//Publish
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam final int newspaperId, RedirectAttributes redir) {
	ModelAndView result;
	Newspaper newspaper;
	try {
		newspaper = this.newspaperService.findOne(newspaperId);
		this.newspaperService.delete(newspaper);
		result = new ModelAndView("redirect:../list.do");
		String successfulMessage = "newspaper.commit.ok";
		redir.addFlashAttribute("message", successfulMessage);
	} catch (Throwable oops) {
		result = new ModelAndView("redirect:../list.do");
		String successfulMessage = "newspaper.commit.error";
		redir.addFlashAttribute("message", successfulMessage);
	}

	return result;
}
	
}
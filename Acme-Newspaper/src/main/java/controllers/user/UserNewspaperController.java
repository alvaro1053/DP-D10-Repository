
package controllers.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;

import domain.Newspaper;
import domain.User;
import forms.NewspaperForm;

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
		
		//Añadimos los no-publicados por el usuario
		newspapers.addAll(principal.getNewspapers());
		//Lo paso a Set para que no haya duplicados
		Set<Newspaper> noDuplicates = new HashSet<Newspaper>(newspapers);
		
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", noDuplicates);
		result.addObject("principal",principal);
		result.addObject("uri", uri);
		return result;
	}	
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		NewspaperForm newspaper = new NewspaperForm();

		result = this.createEditModelAndView(newspaper);

		return result;
	}

	// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final NewspaperForm newspaperForm, final BindingResult binding) {
			ModelAndView result;
			Newspaper newspaper = this.newspaperService.reconstruct(newspaperForm,binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(newspaperForm);
			else
				try {
					this.newspaperService.save(newspaper);
					result = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					String errorMessage = "newspaper.commit.error";
					result = this.createEditModelAndView(newspaperForm, errorMessage);
				}

			return result;
		}

	//Publish
		@RequestMapping(value = "/publish", method = RequestMethod.GET)
		public ModelAndView publish(@RequestParam final int newspaperId, RedirectAttributes redir) {
		ModelAndView result;
		Newspaper newspaper;
		try {
			newspaper = this.newspaperService.findOne(newspaperId);
			this.newspaperService.publish(newspaper);
			result = new ModelAndView("redirect:list.do");
			String successfulMessage = "newspaper.commit.ok";
			redir.addFlashAttribute("message", successfulMessage);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			String successfulMessage = "newspaper.commit.error";
			redir.addFlashAttribute("message", successfulMessage);
		}

		return result;
	}
		
		
	// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(final NewspaperForm newspaper) {
			ModelAndView result;

			result = this.createEditModelAndView(newspaper, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final NewspaperForm newspaper, final String message) {
			ModelAndView result;

			result = new ModelAndView("newspaper/edit");
			result.addObject("newspaper", newspaper);
			result.addObject("message", message);

			return result;
		}

}

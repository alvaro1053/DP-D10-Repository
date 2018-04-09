
package controllers;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Newspaper;

import services.ActorService;
import services.NewspaperService;


@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService	actorService;

	// Constructors

	public NewspaperController() {
		super();
	}

	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Actor principal = this.actorService.findByPrincipal();
		ModelAndView result;
		Collection<Newspaper> newspapers;
		final String uri = "";
		newspapers = this.newspaperService.publishedNewspapers();
		result = new ModelAndView("newspaper/list");
		if(principal != null){
			result.addObject("principal",principal);
		}
		result.addObject("newspapers", newspapers);
		result.addObject("uri", uri);
		return result;
	}	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam("keyword") final String keyword) {
		ModelAndView result;
		final Collection<Newspaper> newspapers;
		final Collection<Newspaper> results;
		final String uri = "";
		Boolean searching;

		results = this.newspaperService.searchNewspapers(keyword);
		newspapers = this.newspaperService.publishedNewspapers();
		newspapers.retainAll(results);

		searching = true;

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("searching", searching);
		result.addObject("uri", uri);

		return result;

	}


}

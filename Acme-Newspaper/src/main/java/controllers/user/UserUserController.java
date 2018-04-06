
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.ArticleService;
import services.UserService;
import domain.Article;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController{

	//Autowired
	@Autowired
	UserService	userService;
	
	@Autowired
	ArticleService	articleService;


	//Constructor
	public UserUserController() {
		super();
	}

	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		String uri = "/user";
		ModelAndView result;
		final User principal = this.userService.findByPrincipal();
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		result.addObject("uri", uri);
		return result;
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		final ModelAndView result;
		final User principal = this.userService.findByPrincipal();
		User user;
		final String uri = "/user";
		Collection<Article> articles;


		user = this.userService.findOne(userId);
		articles = this.articleService.articlesPublishedByUser(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("uri", uri);
		result.addObject("articles", articles);
		result.addObject("principal", principal);
		return result;

	}
	


}

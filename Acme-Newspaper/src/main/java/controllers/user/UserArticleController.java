
package controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.UserService;
import domain.Article;
import domain.User;

@Controller
@RequestMapping("/article/user")
public class UserArticleController{

	//Autowired
	@Autowired
	ArticleService	articleService;
	
	@Autowired
	UserService	userService;


	//Constructor
	public UserArticleController() {
		super();
	}



	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		final ModelAndView result;
		Article article;
		final User principal = this.userService.findByPrincipal();
		final String uri = "/user";

		article = this.articleService.findOne(articleId);

		result = new ModelAndView("article/display");
		result.addObject("article", article);
		result.addObject("uri", uri);
		result.addObject("principal", principal);
		return result;

	}
	


}

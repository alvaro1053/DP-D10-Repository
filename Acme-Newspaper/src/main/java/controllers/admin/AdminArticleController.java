
package controllers.admin;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.AdminService;
import services.ArticleService;
import domain.Admin;
import domain.Article;

@Controller
@RequestMapping("/article/admin")
public class AdminArticleController extends AbstractController {

	//Autowired
	@Autowired
	ArticleService	articleService;
	
	@Autowired
	AdminService	adminService;


	//Constructor
	public AdminArticleController() {
		super();
	}


	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Article> articles;
		Admin principal = this.adminService.findByPrincipal();
		final String uri = "admin";
		
		articles = this.articleService.findAll();
		
		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("uri", uri);
		result.addObject("principal", principal);
		return result;
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		final ModelAndView result;
		Article article;
		Admin principal = this.adminService.findByPrincipal();
		final String uri = "admin";

		article = this.articleService.findOne(articleId);

		result = new ModelAndView("article/display");
		result.addObject("article", article);
		result.addObject("uri", uri);
		result.addObject("principal", principal);
		return result;

	}
	


}

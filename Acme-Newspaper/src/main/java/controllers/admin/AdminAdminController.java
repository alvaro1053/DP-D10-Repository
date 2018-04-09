package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Article;
import domain.Newspaper;

import services.ArticleService;
import services.NewspaperService;


@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController {

	@Autowired
	ArticleService articleService;
	
	@Autowired
	NewspaperService newspaperService;
	
	
	
	@RequestMapping(value = "/listArticles", method = RequestMethod.GET)
	public ModelAndView listArticles(){
		ModelAndView result;
		Collection<Article> articles;
		
		articles = this.articleService.findArticlesWithTabooWords();
		
		result = new ModelAndView("admin/listArticles");
		result.addObject("articles", articles);
		
		
		return result;
	}
	
	
	@RequestMapping(value = "/listNewspapers", method = RequestMethod.GET)
	public ModelAndView listNewspapers(){
		ModelAndView result;
		Collection<Newspaper> newspapers;
		
		newspapers = this.newspaperService.findNewspapersWithTabooWords();
		
		result = new ModelAndView("admin/listNewspapers");
		result.addObject("newspapers", newspapers);
		
		
		return result;
	}
	
	
}

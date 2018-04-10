
package controllers.user;


import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;

import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import domain.Article;
import domain.Newspaper;
import domain.User;
import forms.ArticleForm;


@Controller
@RequestMapping("/article/user")
public class UserArticleController extends AbstractController{

	//Autowired
	@Autowired
	ArticleService	articleService;
	
	@Autowired
	NewspaperService	newspaperService;
	
	@Autowired
	UserService	userService;


	//Constructor
	public UserArticleController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String filter) {
		ModelAndView result;
		Collection<Article> articles;
		final User principal = this.userService.findByPrincipal();
		final String uri = "/user";

		articles = this.articleService.findByFilter(filter);
		result = new ModelAndView("article/list");
		result.addObject("uri", uri);
		result.addObject("articles", articles);
		result.addObject("principal", principal);


		return result;

	}
	
	//create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		ArticleForm articleForm;
		Collection<Newspaper> newspapers;
		
		articleForm = this.articleService.create();
		newspapers = this.newspaperService.notPublishedNewspapers();
		
		result = this.createEditModelAndView(articleForm);
		result.addObject("newspapers", newspapers);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ArticleForm articleForm, BindingResult binding,RedirectAttributes redir){
		ModelAndView result;
		Article article;
		
		article = this.articleService.reconstruct(articleForm, binding);
		if(binding.hasErrors()){
			result = this.createEditModelAndView(articleForm);
		}else{
			try{
				this.articleService.save(article);
				result = new ModelAndView("redirect:list.do");
				String successfulMessage = "article.commit.ok";
				redir.addFlashAttribute("message", successfulMessage);
			}catch(Throwable oops){
				result = this.createEditModelAndView(articleForm, "article.commit.error");
			}
		}
		
		
		return result;
		
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

	
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId, RedirectAttributes redir){
		ModelAndView result;
		Article article;
		User principal = this.userService.findByPrincipal();
		try{
		article = this.articleService.findOne(articleId);
		Assert.isTrue(article.getUser().equals(principal));
		result = this.createEditModelAndView(article);
		} catch (Throwable oops){
		result = new ModelAndView("redirect:/article/list.do");
		redir.addFlashAttribute("message", "article.permision");
		
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ArticleForm articleForm, final BindingResult binding) {
		ModelAndView result;
		Article article;
		User principal;

		principal = this.userService.findByPrincipal();
		article = this.articleService.reconstruct(articleForm, binding);


		if (binding.hasErrors()) {
			result = this.createEditModelAndView(articleForm);
		} else
			try {
				this.articleService.save(article);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				articleForm.setIsDraft(true);
				result = this.createEditModelAndView(articleForm, "article.commit.error");
			}


		result.addObject("principal",principal);
		return result;
	}
	
	
	
	
	
	
	//Ancillary methods
	
		protected ModelAndView createEditModelAndView(ArticleForm article) {
			ModelAndView result; 
			
			result = this.createEditModelAndView(article, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(ArticleForm articleForm,
				String message) {
			ModelAndView result;
			Collection<Newspaper> newspapers;

			newspapers = this.newspaperService.notPublishedNewspapers();
			
			result = new ModelAndView("article/edit");
			result.addObject("articleForm", articleForm);
			result.addObject("message", message);
			result.addObject("newspapers", newspapers);

			return result;
		}
		
		
		protected ModelAndView createEditModelAndView(final Article article) {
			ModelAndView result;

			result = this.createEditModelAndView(article, null);

			return result;
		}

		private ModelAndView createEditModelAndView(final Article article, String message) {

			ModelAndView result;
			Collection<Newspaper> newspapers;
			newspapers = this.newspaperService.notPublishedNewspapers();
			
			ArticleForm articleForm = new ArticleForm();
			
			articleForm = this.articleService.reconstructForm(article);
			
			result = new ModelAndView("article/edit");
			result.addObject("articleForm", articleForm);
			result.addObject("newspapers", newspapers);
			result.addObject("message", message);

			return result;
		}


}

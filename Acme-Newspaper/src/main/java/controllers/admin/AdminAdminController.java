package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Article;
import domain.Chirp;
import domain.Newspaper;

import services.ArticleService;
import services.ChirpService;
import services.NewspaperService;


@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController {

	@Autowired
	ArticleService articleService;
	
	@Autowired
	NewspaperService newspaperService;
	
	@Autowired
	ChirpService	chirpService;
	
	
	
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
	
	@RequestMapping(value = "/listChirps", method = RequestMethod.GET)
	public ModelAndView listChirps(){
		ModelAndView result;
		Collection<Chirp> chirps;
		
		chirps = this.chirpService.findChirpsWithTabooWords();
		
		result = new ModelAndView("admin/listChirps");
		result.addObject("chirps", chirps);
		
		
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int chirpId, RedirectAttributes redir ){
		ModelAndView result;
		Chirp chirp;
		
		chirp = this.chirpService.findOne(chirpId);
		Assert.notNull(chirp);
		

		try{
			this.chirpService.delete(chirp);
			result = new ModelAndView("redirect:listChirps.do");
			redir.addFlashAttribute("message", "chirp.deleted.successfully");
		}catch(Throwable oops){
			result = new ModelAndView("redirect:listChirps.do");
			redir.addFlashAttribute("message", "chirp.commit.error");
		}

		
		return result;
	}
	
	
}

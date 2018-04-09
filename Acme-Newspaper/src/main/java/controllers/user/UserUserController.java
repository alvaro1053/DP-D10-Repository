
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
		
		users.remove(principal);

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
	
	//Display Personal profile
	@RequestMapping(value = "/displayUserProfile", method = RequestMethod.GET)
	public ModelAndView displayPersonalProfile(){
		ModelAndView result;
		User principal;
		Collection<Article> articles;
		String uri;
		
		uri = "/user";
		
		principal = this.userService.findByPrincipal();
		
		articles = this.articleService.articlesPublishedByUser(principal.getId());
		
		result = new ModelAndView("user/display");
		result.addObject("user", principal);
		result.addObject("principal",principal);
		result.addObject("uri", uri);
		result.addObject("articles", articles);
		
		return result;
	}
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam final int userId){
		ModelAndView result;
		User principal;
		User userToBeFollowed;
		
		principal = this.userService.findByPrincipal();
		userToBeFollowed = this.userService.findOne(userId);
		
		
		if(userId != principal.getId()){
			this.userService.follow(userToBeFollowed);
			result = new ModelAndView("redirect:/user/user/list.do");
		}else{
			result = new ModelAndView("redirect:/user/user/list.do");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam final int userId){
		ModelAndView result;
		User principal;
		User userToBeUnfollowed;
		
		principal = this.userService.findByPrincipal();
		userToBeUnfollowed = this.userService.findOne(userId);
		
		if(userId != principal.getId()){
			this.userService.unfollow(userToBeUnfollowed);
			result = new ModelAndView("redirect:/user/user/list.do");
		}else{
			result = new ModelAndView("redirect:/user/user/list.do");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/followingList", method = RequestMethod.GET)
	public ModelAndView followingList(){
		ModelAndView result;
		Collection<User> users;
		User principal;
		String uri;
		Boolean followingTitle = true;
		
		uri = "/user";
		
		principal= this.userService.findByPrincipal();
		
		users = principal.getFollows();
				
		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		result.addObject("uri", uri);
		result.addObject("followingTitle", followingTitle);
		
		return result;
	}

	

	@RequestMapping(value = "/followersList", method = RequestMethod.GET)
	public ModelAndView followersList(){
		ModelAndView result;
		Collection<User> users;
		User principal;
		String uri;
		Boolean followersTitle = true;
		uri = "/user";
		
		principal= this.userService.findByPrincipal();
		
		users = principal.getFollowers();
				
		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		result.addObject("uri", uri);
		result.addObject("followersTitle", followersTitle);
		
		return result;
	}

	


}

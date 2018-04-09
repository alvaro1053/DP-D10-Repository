package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import domain.Admin;
import domain.Article;
import domain.Newspaper;

import domain.User;
import forms.ArticleForm;

@Service
@Transactional
public class ArticleService {

	//Managed Repository ----
	@Autowired
	private ArticleRepository	articleRepository;
	//Services
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private Validator			validator;

	//Constructors
	public ArticleService() {
		super();
	}

	public ArticleForm create() {
		User principal;
		ArticleForm articleForm;
		Date moment;

		principal = userService.findByPrincipal();
		Assert.notNull(principal);

		moment = new Date(System.currentTimeMillis() - 1);
		
		articleForm = new ArticleForm();
		articleForm.setMoment(moment);
	
		return articleForm;
	}
	
	public Collection<Article> findAll() {
		Collection<Article> result;

		result = this.articleRepository.findAll();

		return result;
	}

	public void delete(final Article article) {
		Admin admin;
		Collection<Article> updated, updated2;
		Assert.notNull(article);
		
		admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);

		final Newspaper newspaper = article.getNewspaper();
		final Collection<Article> article1 = newspaper.getArticles();
		updated = new ArrayList<Article>(article1);
		updated.remove(article);
		newspaper.setArticles(updated);

		final User user = article.getUser();
		final Collection<Article> article2 = user.getArticles();
		updated2 = new ArrayList<Article>(article2);
		updated2.remove(article);
		user.setArticles(updated2);

		this.articleRepository.delete(article);

	}

	public Article save(final Article article) {
		Article result;
		
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		
		article.setMoment(new Date(System.currentTimeMillis() - 1));
	
		result = this.articleRepository.save(article);
		
		final Collection<Article> update = principal.getArticles();
		update.add(result);
		principal.setArticles(update);

		final Newspaper newspaper = result.getNewspaper();
		final Collection<Article> update2 = newspaper.getArticles();
		update2.add(result);
		newspaper.setArticles(update2);
		
		return result;
	}

	public Article findOne(final int articleId) {
		Article result;

		result = this.articleRepository.findOne(articleId);
		Assert.notNull(result);

		return result;
	}
	
	public Collection<Article> articlesPublishedByUser(final int userId){
		Collection<Article> result;
		
		result = this.articleRepository.articlesPublishedByUser(userId);
		Assert.notNull(result);
		
		return result;
	}

	public Collection<Article> findByFilter(final String filter) {
		Collection<Article> articles = new ArrayList<Article>();
		if(filter == ""|| filter== null){
			articles = this.findAll();
		} else{
		articles = this.articleRepository.findByFilter(filter);
		}
		return articles;
	}
	
	public Article reconstruct(ArticleForm articleForm, BindingResult binding) {
		Article result = new Article();
		User principal;
		
		principal = this.userService.findByPrincipal();
		
		result.setTitle(articleForm.getTitle());
		result.setMoment(articleForm.getMoment());
		result.setSummary(articleForm.getSummary());
		result.setBody(articleForm.getBody());
		result.setPhotosURL(articleForm.getPhotosURL());
		result.setIsDraft(articleForm.getIsDraft());
		result.setNewspaper(articleForm.getNewspaper());
		result.setUser(principal);
		
		this.validator.validate(result, binding);
		
		return result;
	}
	
	public ArticleForm reconstructForm(final Article article) {
		ArticleForm result;

		result = new ArticleForm();

		result.setId(article.getId());
		result.setVersion(article.getVersion());
		result.setTitle(article.getTitle());
		result.setSummary(article.getSummary());
		result.setBody(article.getBody());
		result.setMoment(article.getMoment());
		result.setPhotosURL(article.getPhotosURL());
		result.setIsDraft(article.getIsDraft());
		result.setNewspaper(article.getNewspaper());

		return result;
	}
	
}
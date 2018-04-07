package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ArticleRepository;
import domain.Admin;
import domain.Article;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class ArticleService {

	//Managed Repository ----
	@Autowired
	private ArticleRepository	articleRepository;
	//Services
	private UserService userService;
	private AdminService adminService;

	//Constructors
	public ArticleService() {
		super();
	}

	public Article create() {
		Article result = new Article();
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		
		result.setUser(principal);
		result.setFollowUps(new ArrayList<FollowUp>());
		result.setPhotosURL(new ArrayList<String>());
	

		return result;
	}

	public Collection<Article> findAll() {
		Collection<Article> result;

		result = this.articleRepository.findAll();

		return result;
	}

	public void delete(final Article article) {
		Admin principal = adminService.findByPrincipal();
		Assert.notNull(principal);
		
		User creator = article.getUser();
		Collection<Article> creatorsArticle = creator.getArticles();
		creatorsArticle.remove(article);
		creator.setArticles(creatorsArticle);
		
		Newspaper newspaper = article.getNewspaper();
		Collection<Article> newspaperArticles = newspaper.getArticles();
		newspaperArticles.remove(article);
		newspaper.setArticles(creatorsArticle);

		this.articleRepository.delete(article);

	}

	public Article save(final Article article) {
		Article result;
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		
		article.setMoment(new Date(System.currentTimeMillis() - 1));

		
		User creator = article.getUser();
		Collection<Article> creatorsArticle = creator.getArticles();
		creatorsArticle.add(article);
		creator.setArticles(creatorsArticle);
		
		Newspaper newspaper = article.getNewspaper();
		Collection<Article> newspaperArticles = newspaper.getArticles();
		newspaperArticles.add(article);
		newspaper.setArticles(creatorsArticle);
		
		result = this.articleRepository.save(article);
		return result;
	}

	public Article findOne(final int articleId) {
		Article result;

		result = this.articleRepository.findOne(articleId);
		Assert.notNull(result);

		return result;
	}

}
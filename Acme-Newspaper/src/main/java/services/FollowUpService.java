package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FollowUpRepository;
import domain.Article;
import domain.FollowUp;
import domain.User;

@Service
@Transactional
public class FollowUpService {

	//Managed Repository ----
	@Autowired
	private FollowUpRepository	followUpRepository;
	//Services
	private UserService userService;

	//Constructors
	public FollowUpService() {
		super();
	}

	public FollowUp create() {
		FollowUp result;
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);

		result = new FollowUp();

		return result;
	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> result;

		result = this.followUpRepository.findAll();

		return result;
	}

	public FollowUp save(final FollowUp followUp) {
		FollowUp result;
		Assert.isTrue(followUp.getId() == 0);
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(followUp.getArticle().getIsDraft() == false || followUp.getArticle().getNewspaper().getIsPublished() == true);
		followUp.setPublicationDate(new Date(System.currentTimeMillis() - 1));	
		result = this.followUpRepository.save(followUp);
		
		Article article = followUp.getArticle();
		Collection<FollowUp> articlesFollowUps = article.getFollowUps();
		articlesFollowUps.add(result);
		article.setFollowUps(articlesFollowUps);
		
		return result;
	}

	public FollowUp findOne(final int followUpId) {
		FollowUp result;

		result = this.followUpRepository.findOne(followUpId);
		Assert.notNull(result);

		return result;
	}


}
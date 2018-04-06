package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NewspaperRepository;
import domain.Admin;
import domain.Article;
import domain.Newspaper;
import domain.Subscription;
import domain.User;

@Service
@Transactional
public class NewspaperService {

	//Managed Repository ----
	@Autowired
	private NewspaperRepository	newspaperRepository;
	//Services
	private UserService userService;
	private AdminService adminService;

	//Constructors
	public NewspaperService() {
		super();
	}

	public Newspaper create() {
		Newspaper result;
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		result = new Newspaper();
		result.setUser(principal);
		result.setArticles(new ArrayList<Article>());
		result.setSubscriptions(new ArrayList<Subscription>());
		return result;
	}

	public Collection<Newspaper> findAll() {
		Collection<Newspaper> result;

		result = this.newspaperRepository.findAll();

		return result;
	}

	public void delete(final Newspaper newspaper) {
		Admin principal = adminService.findByPrincipal();
		Assert.notNull(principal);
		
		User creator = newspaper.getUser();
		Collection<Newspaper> creatorsNewspapers = creator.getNewspapers();
		creatorsNewspapers.remove(newspaper);
		creator.setNewspapers(creatorsNewspapers);
		
		this.newspaperRepository.delete(newspaper);

	}

	public Newspaper save(final Newspaper newspaper) {
		Newspaper result;
		User principal = userService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(newspaper.getUser().equals(principal));
		
		if(newspaper.getPublicationDate() != null){
			newspaper.setPublicationDate(new Date(System.currentTimeMillis() - 1));
		}
		
		result = this.newspaperRepository.save(newspaper);
		
		Collection<Newspaper> creatorsNewspapers = principal.getNewspapers();
		creatorsNewspapers.add(result);
		principal.setNewspapers(creatorsNewspapers);
	
		return result;
	}

	public Newspaper findOne(final int newspaperId) {
		Newspaper result;

		result = this.newspaperRepository.findOne(newspaperId);
		Assert.notNull(result);

		return result;
	}
	
	public Collection<Newspaper> publishedNewspapers(){
		Collection<Newspaper>result;
		
		result = this.newspaperRepository.publishedNewspapers();
		Assert.notNull(result);
		
		return result;
	}


}
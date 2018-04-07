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

import services.UserService;

import repositories.NewspaperRepository;
import domain.Admin;
import domain.Article;
import domain.Newspaper;
import domain.Subscription;
import domain.User;
import forms.NewspaperForm;

@Service
@Transactional
public class NewspaperService {

	//Managed Repository ----
	@Autowired
	private NewspaperRepository	newspaperRepository;
	//Services
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private Validator validator;

	//Constructors
	public NewspaperService() {
		super();
	}

	public Newspaper create() {
		Newspaper result;
		User principal = this.userService.findByPrincipal();
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
		
		if(newspaper.getPublicationDate() == null){
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
	
	public void publish (Newspaper newspaper){
		User principal = this.userService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getNewspapers().contains(newspaper));
		Date now = new Date(System.currentTimeMillis());
		Assert.isTrue(newspaper.getPublicationDate().after(now));
		for (Article a : newspaper.getArticles()){
			Assert.isTrue(a.getIsDraft() == false);
		}
		newspaper.setPublicationDate(new Date(System.currentTimeMillis() - 1));
	}
	
	public Newspaper reconstruct (NewspaperForm newspaperForm, BindingResult binding){
		Newspaper newspaper = this.create();
		
		newspaper.setTitle(newspaperForm.getTitle());
		newspaper.setDescription(newspaperForm.getDescription());
		newspaper.setPictureURL(newspaperForm.getPictureURL());
		newspaper.setIsPrivate(newspaperForm.getIsPrivate());
		newspaper.setId(newspaperForm.getId());
		newspaper.setVersion(newspaperForm.getVersion());
		newspaper.setPublicationDate(newspaperForm.getPublicationDate());
		
		validator.validate(newspaperForm, binding);
		return newspaper;
	}


}
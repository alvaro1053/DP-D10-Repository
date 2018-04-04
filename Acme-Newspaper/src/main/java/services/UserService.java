
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Article;
import domain.Chirp;
import domain.Newspaper;

import domain.User;
import forms.ActorForm;

@Service
public class UserService {

	// Managed Repository
	@Autowired
	private UserRepository	UserRepository;
	
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private Validator		validator;



	// Supporting services

	// Constructors

	public UserService() {
		super();
	}

	// Simple CRUD methods
	public User create() {
		User result;

		result = new User();
		result.setArticles(new ArrayList<Article>());
		result.setChirps(new ArrayList<Chirp>());
		result.setFollowers(new ArrayList<User>());
		result.setFollows(new ArrayList<User>());
		result.setNewspapers(new ArrayList<Newspaper>());

		return result;
	}

	public User save(final User User) {
		User saved;
		Assert.notNull(User);
		Actor principal = null;
		
		try{
			principal =  this.actorService.findByPrincipal();
		}catch(Throwable oops){
		}
		
		//TEST ASSERT - Testing if someone is trying to register while he/she is already being registered to the system at the moment
		Assert.isTrue(principal == null);
		//TEST ASSERT ======================================
		

		if (User.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			User.getUserAccount().setPassword(passwordEncoder.encodePassword(User.getUserAccount().getPassword(), null));
		}
		

		saved = this.UserRepository.save(User);
		
		//TEST ASSERT - Testing if the user is in the system after saving him/her
		Assert.isTrue(this.UserRepository.findAll().contains(saved));
		//TEST ASSERT =========================================
		return saved;
	}

	public User findOne(final int UserId) {
		User result;
		result = this.UserRepository.findOne(UserId);
		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result;
		result = this.UserRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	//Other business methods
	public User findByPrincipal() {
		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;
		result = this.UserRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public User reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final User user = this.create();
		user.setName(actorForm.getName());
		user.setSurname(actorForm.getSurname());
		user.setEmail(actorForm.getEmail());
		user.setId(actorForm.getId());
		user.setPostalAddress(actorForm.getAddress());
		user.setVersion(actorForm.getVersion());
		user.setPhone(actorForm.getPhone());
		user.setUserAccount(actorForm.getUserAccount());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("USER");
		authorities.add(auth);
		user.getUserAccount().setAuthorities(authorities);

		this.validator.validate(actorForm, binding);
		if (!(actorForm.getConfirmPassword().equals((actorForm.getUserAccount().getPassword()))) || actorForm.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "user.passwordMiss");
		if ((actorForm.getCheck() == false))
			binding.rejectValue("check", "user.uncheck");
		return user;
	}
	
	public void flush(){
		this.UserRepository.flush();
	}
}


package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.CreditCard;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;
import forms.SubscriptionForm;


@Service
@Transactional
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository		subscriptionRepository;
	
	@Autowired
	private CustomerService				customerService;

	@Autowired
	private Validator					validator;

	// Constructors
	public SubscriptionService() {
		super();
	}

	// Simple CRUD methods
	public Subscription create() {
		Customer principal;
		Subscription subscription = new Subscription();
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		subscription.setCustomer(principal);
		subscription = new Subscription();

		return subscription;
	}

	public Subscription save(final Subscription subscription) {
		Customer principal;
		Subscription result;
		List<Subscription> updated,updated2;

		Assert.notNull(subscription);

		principal = this.customerService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(subscription.getNewspaper().getIsPrivate() == true);
		for(Subscription s: principal.getSubscriptions()){
			Assert.isTrue(!(s.getNewspaper().equals(subscription.getNewspaper())));
		}

		result = this.subscriptionRepository.save(subscription);
		
		Customer customer = result.getCustomer();
		final Collection<Subscription> subscriptions = customer.getSubscriptions();
		updated = new ArrayList<Subscription>(subscriptions);
		updated.add(result);
		customer.setSubscriptions(updated);
		
		Newspaper newspaper = result.getNewspaper();
		Collection<Subscription> subscriptions2 = newspaper.getSubscriptions();
		updated2 = new ArrayList<Subscription>(subscriptions2);
		updated2.add(result);
		newspaper.setSubscriptions(updated2);

		return result;
	}

	

	public Subscription findOne(final int id) {
		final Customer principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		final Subscription res = this.subscriptionRepository.findOne(id);
		return res;
	}

	public Subscription reconstruct(final SubscriptionForm subscription, final BindingResult binding){
		Subscription result = this.create();
		
			
			result.setId(subscription.getId());
			result.setVersion(subscription.getVersion());
			result.setCreditCard(subscription.getCreditCard());
			Customer customer = this.customerService.findByPrincipal();
			result.setCustomer(customer);
			result.setNewspaper(subscription.getNewspaper());
			
		this.validator.validate(result, binding);
		return result;
		
		
	}
	public CreditCard reconstructCreditCard(String cookie){
		String[] creditCard= cookie.split("\\.",6);
		String holder = creditCard[0];
		String brand = creditCard[1];
		String number = creditCard[2];
		Integer expirationMonth = Integer.parseInt(creditCard[3]);
		Integer expirationYear = Integer.parseInt(creditCard[4]);
		Integer CVV = Integer.parseInt(creditCard[5]);
		
		CreditCard creditCard2 = new CreditCard();
		creditCard2.setHolderName(holder);
		creditCard2.setBrandName(brand);
		creditCard2.setNumber(number);
		creditCard2.setExpirationMonth(expirationMonth);
		creditCard2.setExpirationYear(expirationYear);
		creditCard2.setCVV(CVV);
		
		
		return creditCard2;
	}
	
	public void flush() {
	this.subscriptionRepository.flush();
		
	}
}

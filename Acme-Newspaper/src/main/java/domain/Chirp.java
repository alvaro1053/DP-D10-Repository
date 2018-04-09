
package domain;


import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity {

	private Date		moment;
	private String		title;
	private String		description;
	private User 		user;
	private Boolean		tabooWords;
	
		
	
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getTabooWords() {
		return tabooWords;
	}
	public void setTabooWords(Boolean tabooWords) {
		this.tabooWords = tabooWords;
	}

//Relationship
	@ManyToOne(optional = true)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

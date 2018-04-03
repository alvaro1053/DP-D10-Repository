
package domain;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Access(AccessType.PROPERTY)
public class Article extends DomainEntity {

	private String					title;
	private Date					moment;
	private String					summary;
	private String					body;
	private String					photosURL;
	private Boolean					isDraft;
	
	private Newspaper 				newspaper;
	private User 					user;
	private Collection<FollowUp> 	followUps;
	
	
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@NotBlank
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@URL
	public String getPhotosURL() {
		return photosURL;
	}
	public void setPhotosURL(String photosURL) {
		this.photosURL = photosURL;
	}
	
	@NotNull
	public Boolean getIsDraft() {
		return isDraft;
	}
	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}
	
	


//Relationships	
	
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return newspaper;
	}
	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}
	
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<FollowUp> getFollowUp() {
		return followUps;
	}
	public void setFollowUp(Collection<FollowUp> followUps) {
		this.followUps = followUps;
	}
	

}

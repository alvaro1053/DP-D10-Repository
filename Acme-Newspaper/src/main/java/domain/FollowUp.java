
package domain;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;




@Entity
@Access(AccessType.PROPERTY)
public class FollowUp extends DomainEntity {

	private String		title;
	private Date		publicationDate;
	private String		summary;
	private String		text;
	private Collection<String>		photosURL;
	
	private Article 	article;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ElementCollection
	public Collection<String> getPhotosURL() {
		return photosURL;
	}
	public void setPhotosURL(Collection<String> photosURL) {
		this.photosURL = photosURL;
	}

//Relationship
	@ManyToOne(optional = true)
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
}

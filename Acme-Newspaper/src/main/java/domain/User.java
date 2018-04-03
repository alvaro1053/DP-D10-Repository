
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private Collection<Chirp> 		chrips;
	private Collection<User> 		users;
	private Collection<Newspaper> 	newspapers;
	private Collection<Article>		articles;
	
	
	@OneToMany(mappedBy="user")
	public Collection<Chirp> getChrips() {
		return chrips;
	}
	public void setChrips(Collection<Chirp> chrips) {
		this.chrips = chrips;
	}
	
	@ManyToMany
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	@OneToMany(mappedBy="user")
	public Collection<Newspaper> getNewspapers() {
		return newspapers;
	}
	public void setNewspapers(Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}
	
	@OneToMany(mappedBy="user")
	public Collection<Article> getArticles() {
		return articles;
	}
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}
	
}

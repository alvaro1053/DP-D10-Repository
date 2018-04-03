
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;


@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private Collection<Newspaper>	newspapers;

	
	@ManyToMany
	public Collection<Newspaper> getNewspapers() {
		return newspapers;
	}

	public void setNewspapers(Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}


}

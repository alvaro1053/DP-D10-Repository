package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {
	
	@Query("select n from Newspaper n where n.publicationDate < CURRENT_TIMESTAMP")
	Collection<Newspaper> publishedNewspapers();
	
	@Query("select n from Newspaper n where n.title LIKE %?1% or n.description LIKE %?1%")
	Collection<Newspaper> searchNewspapers(String keyword);

}
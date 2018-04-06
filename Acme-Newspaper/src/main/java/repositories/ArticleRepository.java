package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("select a from User u join u.newspapers n join n.articles a where n.publicationDate!= null and u.id=?1")
	Collection<Article> articlesPublishedByUser(int userId);
	
}
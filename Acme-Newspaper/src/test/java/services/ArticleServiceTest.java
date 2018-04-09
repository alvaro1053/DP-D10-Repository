package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Article;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class ArticleServiceTest extends AbstractTest {

	@Autowired
	private ArticleService articleService;
	
	@Test
	public void driverSearchArticle(){
		Object testingData[][] = {
								//Requisitos C 6.4: Search for a published article using a single key word that must appear somewhere
								//in its title, summary, or body.
								//==========================================================================//

								//Tests POSITIVOS 
								//
								//Un test en el que buscamos messi, hay un artículo sobre messi así que debe devolverlo 
								{"user1", "messi", null},
								//Tests NEGATIVOS
								//No hay nada con esa palabra, por lo que debe dar fallo el Assert del test que comprueba que la lista no esté vacía
								{"user1", "curifisqui", IllegalArgumentException.class},
								//Logueandome con un usuario que noe existe
								{"customer34", "estoesuncasodepruebanegativo",  IllegalArgumentException.class},
								//Con un customer que sí existe pero que  devuelve la lista vacía
								{"customer1", "estoesuncasodepruebanegativo",  IllegalArgumentException.class},
								
		};
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateCreateComment(((String) testingData[i][0]), (String) testingData[i][1],  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void templateCreateComment(String username, String filter, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			Collection<Article> res = new ArrayList<Article>();
			res = this.articleService.findByFilter(filter);
			Assert.isTrue(!res.isEmpty());
			unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
		
	}


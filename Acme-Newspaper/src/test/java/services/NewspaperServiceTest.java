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


import domain.Newspaper;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class NewspaperServiceTest extends AbstractTest {

	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	public void diverListNewspaper(){ 
		Object testingData[][]= {
				
/*
  4.2 An actor who is not authenticated must be able to: List the newspapers that are published and browse their articles.
*/
				//==========================================================================//
				//Tests POSITIVOS 
				//compruebo que puedo listar los newspaper y ver los articles del newspaper 1 sin estar logeado
				{null, "newspaper1",null},
				//compruebo que puedo listar los newspaper y ver los articles del newspaper 1 logeado como user
				{"user1", "newspaper1", null},
				//compruebo que puedo listar los newspaper y ver los articles del newspaper 1 logeado como admin
				{"admin", "newspaper1", null},
				//compruebo que puedo listar los newspaper y ver los articles del newspaper 1 logeado como customer
				{"customer1", "newspaper1", null},
				{"user1", "newspaper2", null},

		};
		for (int i = 0; i < testingData.length; i++){
			templateListNewspaper((String) testingData[i][0], super.getEntityId((String) testingData[i][1]),(Class<?>) testingData[i][2]);
		}
	}
	
	protected void templateListNewspaper(final String username, final int newspaperId, final Class<?> expected){
		
		Class<?> caught;
		Newspaper newspaper;
		caught = null;
		
		try{
			authenticate(username);
			this.newspaperService.publishedNewspapers();
			newspaper = this.newspaperService.findOne(newspaperId);
			if(newspaper.getIsPrivate()){
				
			}
			this.articleService.articlesOfNewspaper(newspaperId);
			unauthenticate();
		} catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverSearchNewspaper(){
		Object testingData[][] = {
								//Requisitos C: 6.5: Search for a published newspaper using a single keyword that must appear somewhere
								//in its title or its description.
								//==========================================================================//

								//Tests POSITIVOS 
								//
								//Un test en el que buscamos marca, hay un newspaper llamado marca así que debe devolverlo 
								{"user1", "Marca", null},
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
			templateSearchNewspaper(((String) testingData[i][0]), (String) testingData[i][1],  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void templateSearchNewspaper(String username, String filter, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			Collection<Newspaper> res = new ArrayList<Newspaper>();
			res = this.newspaperService.findByFilter(filter);
			Assert.isTrue(!res.isEmpty());
			unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	@Test
	public void driverChangePrivacity(){
		Object testingData[][] = {
//				23. An actor who is authenticated as a user must be able to:
				//1. Decide on whether a newspaper that he or she’s created is public or private.
								//==========================================================================//

								//Tests POSITIVOS 
								//
								//El user 1 es propietario del newspaper 1 por lo que no debe haber problema
								{"user1", "newspaper1", null},
								//Tests NEGATIVOS
								//Un user intentando cambiar la privacidad de un newspaper que no le corresponde
								{"user3", "newspaper1", IllegalArgumentException.class},
								//Intentar cambiarlo con otro rol
								{"admin", "newspaper2",  IllegalArgumentException.class},
								
		};
		for (int i = 0; i < testingData.length; i++){
			this.startTransaction();
			templateChangePrivacity(((String) testingData[i][0]), this.getEntityId((String) testingData[i][1]),  (Class<?>) testingData[i][2]);
			this.rollbackTransaction();
		}
	}

	protected void templateChangePrivacity(String username, int newspaperId, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try{
			super.authenticate(username);
			this.newspaperService.changePrivacity(newspaperId);
		
			unauthenticate();
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	

	
		@Test
		public void driverDelete() {
			final Object testingData[][] = {
					//Requisito C 7.1 An actor who is authenticated as an administrator must be able to: Remove an newspaper that he or she thinks is inappropriate.
					
					//TEST POSITIVO
					//Pruebo que el admin puede eliminar el artículo 3
					{"admin", "newspaper1", null}, 
					//
					//==========================================================================//

					//TEST NEGATIVO
					//
					//Se elimina el article4 incorrectamente porque no lo puede eliminar un user
					{"user1", "newspaper1", IllegalArgumentException.class},
					//Se elimina el article1 incorrectamente porque no lo puede eliminar un customer
					{"customer1", "newspaper1", IllegalArgumentException.class}
			};
			for (int i = 0; i < testingData.length; i++)
				this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
		}
		private void templateDelete(final String username, final int newspaperId, final Class<?> expected) {
			Newspaper newspaper;
			Class<?> caught;

			caught = null;
			try {
				super.authenticate(username);
				newspaper = this.newspaperService.findOne(newspaperId);
				this.newspaperService.delete(newspaper);

				this.newspaperService.flush();
			} catch (final Throwable oops) {
				caught = oops.getClass();
			}

			this.checkExceptions(expected, caught);

			super.unauthenticate();
		}

	}

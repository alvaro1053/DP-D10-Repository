package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import domain.User;
import forms.ActorForm;

import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class UserServiceTest extends AbstractTest {
	
	@Autowired
	UserService		userService;
	
	
	@Test
	public void driverRegisterUser(){
		
			Object testingData[][] = {
					{"userPrueba1", "surn1","email@prueba.com", "111222333", "calle1",true,"prueba1", "prueba1pass", "prueba1pass",null},
					{"", "surn2","email@prueba.com", "111222333", "calle2",true,"prueba2", "prueba2pass", "prueba2pass",NullPointerException.class},
					{"userPrueba3", "","email@prueba.com", "111222333", "calle3",true,"prueba3", "prueba3pass", "prueba3pass",NullPointerException.class},
					{"userPrueba4", "surn4","emailMAl", "111222333", "calle4",true,"prueba4", "prueba4pass", "prueba4pass",NullPointerException.class},
					{"userPrueba5", "surn5","", "111222333", "calle5",true,"prueba5", "prueba5pass", "prueba5pass",NullPointerException.class},
					{"userPrueba6", "surn6","email@prueba.com", "111222333", "calle6",true,"prueba6", "prueba6pass", "mal",NullPointerException.class},
					{"userPrueba7", "surn7","email@prueba.com", "111222333", "calle7",false,"prueba7", "prueba7pass", "prueba7pass",NullPointerException.class},
					{"userPrueba8", "surn8","email@prueba.com", "111222333", "calle8",true,"", "prueba8pass", "prueba8pass",NullPointerException.class},
					{"userPrueba9", "surn9","email@prueba.com", "111222333", "calle8",true,"user1", "prueba9pass", "prueba9pass",DataIntegrityViolationException.class},
					{"userPrueba10", "surn10","email@prueba.com", "+111222333", "calle10",true,"prueba10", "prueba10pass", "prueba10pass",null},
					
					
			};
			for(int i = 0; i < testingData.length; i++){
				this.startTransaction();
				templateRegisterUser(((String) testingData[i][0]),((String) testingData[i][1]),((String) testingData[i][2]),((String) testingData[i][3]),((String) testingData[i][4]),((Boolean) testingData[i][5]),((String) testingData[i][6]),((String) testingData[i][7]),((String) testingData[i][8]),((Class<?>) testingData[i][9]));
				this.rollbackTransaction();
			}
	}


	protected void templateRegisterUser(String username,String surname, String email,String phone, String address, Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword, Class<?> expected) {
		Class<?> caught = null;
		User user = this.userService.create();
		
		//==== Registrar datos =========
		ActorForm actorForm = registeringActorForm(username, surname, email, phone, address, checkTerms, userAccountName, userAccountPassword, userAccountConfirmPassword);
		BindingResult binding = null;
		try{
			user = this.userService.reconstruct(actorForm, binding);
			user = this.userService.save(user);
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		checkExceptions(expected, caught);
	}


	protected ActorForm registeringActorForm(String name, String surname, String email,String phone, String address, Boolean checkTerms, String userAccountName, String userAccountPassword, String userAccountConfirmPassword) {
		ActorForm actorForm = new ActorForm();
		
		actorForm.setName(name);
		actorForm.setSurname(surname);
		actorForm.setEmail(email);

		actorForm.setPhone(phone);
		actorForm.setAddress(address);
		
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(userAccountName);
		userAccount.setPassword(userAccountPassword);
		actorForm.setConfirmPassword(userAccountConfirmPassword);
		actorForm.setUserAccount(userAccount);
		
		actorForm.setCheck(checkTerms);	
		
		return actorForm;
	}

}

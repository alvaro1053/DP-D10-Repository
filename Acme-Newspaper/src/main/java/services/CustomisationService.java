package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Customisation;

import repositories.CustomisationRepository;

@Service
@Transactional
public class CustomisationService {
	
	@Autowired
	private CustomisationRepository 	customisationRepository;
	
	public CustomisationService(){
		super();
	}

	
	public Customisation findCustomisation(){
		Customisation result;
		
		result = this.customisationRepository.findCustomisation();
		
		return result;
	}
}

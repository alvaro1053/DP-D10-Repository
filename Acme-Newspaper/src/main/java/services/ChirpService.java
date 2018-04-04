
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Admin;
import domain.Chirp;
import domain.User;

@Service
@Transactional
public class ChirpService {

	// Managed Repository
	@Autowired
	private ChirpRepository		chirpRepository;

	// Managed services
	@Autowired
	private UserService			userService;

	@Autowired
	private AdminService		adminService;


	//Constructor
	public ChirpService() {
		super();
	}

	public Chirp create(final int rendeId) {
		User principal;
		Chirp chirp;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		chirp = new Chirp();

		chirp.setMoment(moment);

		return chirp;
	}

	public Chirp save(final Chirp chirp) {
		User principal;
		Date moment;
		Chirp result;

		moment = new Date(System.currentTimeMillis() - 1);

		Assert.isTrue(chirp.getId()==0);//Me aseguro de que no pueda editar un chirp, solo crearlo

		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);

		chirp.setMoment(moment);
		chirp.setUser(principal);

		result = this.chirpRepository.save(chirp);

		return result;
	}

	public void delete(final Chirp chirp) {
		Admin principal;//Solo se puede borrar por un admin
		User user;
		Collection<Chirp> chirps, updated;

		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);

		//Eliminar relaciones

		user = chirp.getUser();
		chirps = user.getChirps();
		updated = new ArrayList<Chirp>(chirps);
		updated.remove(chirp);
		user.setChirps(chirps);

		this.chirpRepository.delete(chirp);

	}


	public Chirp findOne(final int chirpId) {
		final Chirp chirp = this.chirpRepository.findOne(chirpId);
		return chirp;

	}

	public void flush(){
		this.chirpRepository.flush();
	}
}

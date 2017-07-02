package by.tilalis.db;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import by.tilalis.db.interfaces.UserManager;
import by.tilalis.db.records.Role;
import by.tilalis.db.records.User;

@Stateless
public class PGUserManager extends PGManager implements UserManager {	
	private static final int USER_ROLE_ID = 2;
	
	public PGUserManager() {
		super();
	}

	@Override
	public User getUser(String username) {
		User user = null;
		
		try {
			user = em.createNamedQuery("User.findByUsername", User.class)
			.setParameter("username", username)
			.getSingleResult();
		} catch (NoResultException nre) {
		}
		
		return user;
	}

	@Override
	public void addUser(User inserted) {
		inserted.setRole((Role) em.find(Role.class, USER_ROLE_ID));
		em.persist(inserted);
	}

	@Override
	public void deleteUser(User deleted) {
		em.remove(deleted);
	}

	@Override
	public void deleteUserById(int id) {
		final User user = em.find(User.class, id);
		em.remove(user);
	}

}

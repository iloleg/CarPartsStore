package by.tilalis.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public abstract class PGManager {
	@PersistenceContext(unitName = "cps-unit", type = PersistenceContextType.TRANSACTION)
	protected EntityManager em;
	
	public PGManager() {
		
	}
}

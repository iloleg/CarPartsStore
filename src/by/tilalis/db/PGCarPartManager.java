package by.tilalis.db;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import by.tilalis.db.entities.Brand;
import by.tilalis.db.entities.CarPart;
import by.tilalis.db.entities.Category;
import by.tilalis.db.entities.User;
import by.tilalis.db.interfaces.CarPartManager;

@Stateless
public class PGCarPartManager extends PGManager implements CarPartManager {
	public PGCarPartManager() {
		super();
	}

	@Override
	public List<CarPart> getPage(int linesPerPage, int numberOfPage) {
		return getPage(linesPerPage, numberOfPage, null, null);
	}

	@Override
	public List<CarPart> getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery) {
		final TypedQuery<CarPart> query;
		int offset = numberOfPage * linesPerPage;
		
		if (searchField == null || searchQuery == null || "".equals(searchField) || "".equals(searchQuery)) {
			query = em.createNamedQuery("CarPart.getParts", CarPart.class);
			if (linesPerPage > 0) {
				query.setFirstResult(offset).setMaxResults(linesPerPage);
			}
		} else {
			final CriteriaBuilder builder = em.getCriteriaBuilder();
			final CriteriaQuery<CarPart> criteriaQuery = builder.createQuery(CarPart.class);
			final Root<CarPart> root = criteriaQuery.from(CarPart.class);
			final String[] parts = searchField.split("\\.");
			
			Path<Object> path = root.get(parts[0]);
			for (int i = 1; i < parts.length; ++i) {
				path = path.get(parts[i]);
			}
			
			final Predicate where;
			if ("price".equals(searchField) || "factoryId".equals(searchField)) {
				where = builder.equal(path, searchQuery);
			} else {
				where = builder.like(path.as(String.class), "%" + searchQuery + "%");
			}
			
			criteriaQuery.select(root)
			.where(where);
	
			query = em.createQuery(criteriaQuery);
			if (linesPerPage > 0) {
				query.setFirstResult(offset).setMaxResults(linesPerPage);
			}
		}
		
		return query.getResultList();
	}

	@Override
	public List<CarPart> getTrash(int linesPerPage, int numberOfPage) {
		final TypedQuery<CarPart> query = em.createNamedQuery("CarPart.getTrash", CarPart.class);
		if (linesPerPage > 0) {
			int offset = numberOfPage * linesPerPage;
			query.setFirstResult(offset).setMaxResults(linesPerPage);
		}
		return query.getResultList();
	}

	@Override
	public List<Brand> getBrands() {
		return em.createNamedQuery("Brand.getAll", Brand.class).getResultList();
	}

	@Override
	public List<Category> getCategories() {
		return em.createNamedQuery("Category.getAll", Category.class).getResultList();
	}

	@Override
	public int getRowsCount() {
		return 0;
	}

	@Override
	public void untrashRecord(CarPart untrashed) {
		CarPart found = em.find(CarPart.class, untrashed.getId());
		found.setTrash(false);
		em.merge(found);
	}

	@Override
	public void editRecord(CarPart updated) {
		em.merge(updated);
	}

	@Override
	public void addRecord(CarPart inserted) {
		em.persist(inserted);
	}

	@Override
	public void deleteRecord(CarPart deleted) {
		CarPart found = em.find(CarPart.class, deleted.getId());
		if (found.isTrash()) {
			em.remove(found);
		} else {
			found.setTrash(true);
			em.merge(found);
		}
	}

	@Override
	public void addBrand(Brand inserted) {
		em.persist(inserted);
	}

	@Override
	public void addCategory(Category inserted) {
		em.persist(inserted);
	}

	@Override
	public List<User> getUsersTable() {
		return em.createNamedQuery("User.getAll", User.class).getResultList();
	}
}

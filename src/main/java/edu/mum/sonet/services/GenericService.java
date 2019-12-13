package edu.mum.sonet.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {

	List<T> findAll();

	T save(T entity);

	T findById(long id);

	void delete(T entity);

	void deleteById(long id);
}

package edu.mum.sonet.services.impl;

import java.util.List;
import java.util.Optional;


import edu.mum.sonet.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.services.GenericService;

public class GenericServiceImpl<T> implements GenericService<T> {


	private JpaRepository<T, Long> repo;

	public GenericServiceImpl(JpaRepository<T, Long> repo) {
		this.repo = repo;
	}

	@Override
	public List<T> findAll() {
		return repo.findAll();
	}

	@Override
	public T save(T entity) {
		return repo.save(entity);
	}

	@Override
	public T findById(long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found."));
	}

	@Override
	public void delete(T entity) {
		repo.delete(entity);
	}

	@Override
	public void deleteById(long id) {
		repo.deleteById(id);
	}

}

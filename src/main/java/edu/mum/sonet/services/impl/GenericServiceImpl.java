package edu.mum.sonet.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
	public Optional<T> findById(long id) {
		return repo.findById(id);
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

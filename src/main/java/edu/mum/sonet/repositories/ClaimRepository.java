package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Claim;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim,Long> {
	List<Claim> findAllByIsActiveOrderByIdDesc(Boolean isAccepted);
	Boolean existsByUser_Email(String email);
}

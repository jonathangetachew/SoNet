package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Claim;

public interface ClaimRepo extends JpaRepository<Claim,Long>{

}

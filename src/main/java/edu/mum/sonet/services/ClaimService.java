package edu.mum.sonet.services;

import edu.mum.sonet.models.Claim;

import java.util.List;

public interface ClaimService extends GenericService<Claim>{
	List<Claim> findAllActiveClaims();
	Boolean userHasAClaim(String email);
}

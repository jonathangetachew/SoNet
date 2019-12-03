package edu.mum.sonet.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Claim;
import edu.mum.sonet.repositories.ClaimRepository;
import edu.mum.sonet.services.ClaimService;

@Service
@Transactional
public class ClaimServiceImpl extends GenericServiceImpl<Claim> implements ClaimService {

	private ClaimRepository claimRepository;

	@Autowired
	public ClaimServiceImpl(ClaimRepository repo, ClaimRepository claimRepository) {
		super(repo);
		this.claimRepository = claimRepository;
	}

}
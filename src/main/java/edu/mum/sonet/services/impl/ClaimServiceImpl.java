package edu.mum.sonet.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Claim;
import edu.mum.sonet.repositories.ClaimRepo;
import edu.mum.sonet.services.ClaimService;

@Service
@Transactional
public class ClaimServiceImpl extends GenericServiceImpl<Claim> implements ClaimService {

	private ClaimRepo claimRepo;

	@Autowired
	public ClaimServiceImpl(ClaimRepo repo, ClaimRepo claimRepo) {
		super(repo);
		this.claimRepo = claimRepo;
	}

}

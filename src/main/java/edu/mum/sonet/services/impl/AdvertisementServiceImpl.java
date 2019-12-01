package edu.mum.sonet.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.repositories.AdvertisementRepo;
import edu.mum.sonet.services.AdvertisementService;

@Service
@Transactional
public class AdvertisementServiceImpl extends GenericServiceImpl<Advertisement> implements AdvertisementService {

	private AdvertisementRepo advertisementRepo;

	@Autowired
	public AdvertisementServiceImpl(AdvertisementRepo repo, AdvertisementRepo advertisementRepo) {
		super(repo);
		this.advertisementRepo = advertisementRepo;
	}

}

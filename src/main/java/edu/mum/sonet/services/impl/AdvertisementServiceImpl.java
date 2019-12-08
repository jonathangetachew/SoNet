package edu.mum.sonet.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.repositories.AdvertisementRepository;
import edu.mum.sonet.services.AdvertisementService;

@Service
@Transactional
public class AdvertisementServiceImpl extends GenericServiceImpl<Advertisement> implements AdvertisementService {

	private AdvertisementRepository advertisementRepository;

	@Autowired
	public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
		super(advertisementRepository);
		this.advertisementRepository = advertisementRepository;
	}

}

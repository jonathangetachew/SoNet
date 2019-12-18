package edu.mum.sonet.services.impl;


import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.models.User;
import edu.mum.sonet.repositories.AdvertisementRepository;
import edu.mum.sonet.services.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

@Service
@Transactional
public class AdvertisementServiceImpl extends GenericServiceImpl<Advertisement> implements AdvertisementService {

	private AdvertisementRepository advertisementRepository;

	@Autowired
	public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
		super(advertisementRepository);
		this.advertisementRepository = advertisementRepository;
	}

	@Override
	public Set<Advertisement> getAdsForUser(@Valid User currentUser, Pageable pageable) {
		return advertisementRepository.findAdsForUser(currentUser.getLocation(), currentUser.getGender(), LocalDate.now().getYear() - currentUser.getDateOfBirth().getYear(), pageable).toSet();
	}
}

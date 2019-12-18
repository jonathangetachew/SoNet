package edu.mum.sonet.services;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.models.User;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface AdvertisementService extends GenericService<Advertisement>{
    Set<Advertisement> getAdsForUser(User currentUser, Pageable pageable);
}

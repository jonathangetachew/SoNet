package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.Advertisement;

public interface AdvertisementRepo extends JpaRepository<Advertisement,Long>{

}

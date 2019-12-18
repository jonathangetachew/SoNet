package edu.mum.sonet.repositories;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.models.enums.Gender;
import edu.mum.sonet.models.enums.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Query("from Advertisement ads where (ads.location = :location or ads.location = 'NONE') and (ads.targetGender = :gender or ads.targetGender = 'NONE') and ads.targetAge <= :age")
    Page<Advertisement> findAdsForUser(@Param("location") Location location, @Param("gender") Gender targetGender, @Param("age") Integer age, Pageable pageable);
}

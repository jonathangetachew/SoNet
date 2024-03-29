package edu.mum.sonet.repositories;

import edu.mum.sonet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndFollowing(String email, User user);

    //@Query("select u from User u left join fetch u.followers f where u.email =:email")
    //User getUserByEmailFetchFollowers(@Param("email") String email);
}

package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}

package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByEmail(String email);
}

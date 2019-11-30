package edu.mum.sonet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.sonet.models.User;

public interface UserRepo extends JpaRepository<User,Long>{

}

package com.example.gearup.repository;

import com.example.gearup.data.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer>
{
    public List<UserAuth> findByEmail(String email); //abstract

}

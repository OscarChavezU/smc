package com.oscarchavez.smc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oscarchavez.smc.model.User;;

public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByUsuario(String username);
}

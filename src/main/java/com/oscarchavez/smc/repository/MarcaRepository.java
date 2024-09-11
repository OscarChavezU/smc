package com.oscarchavez.smc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oscarchavez.smc.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer>{

    @Query("SELECT m FROM Marca m WHERE m.estado = :estado")
    List<Marca> listaMarcas(@Param("estado") String estado);

    


}

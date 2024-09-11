package com.oscarchavez.smc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oscarchavez.smc.dto.NotaDto;
import com.oscarchavez.smc.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

    @Query(value = "SELECT idnota as idnota, descripcion as descripcion, estado as estado, fecha as fecha, idproducto as idproducto "
            +
            "FROM notas WHERE idproducto = :idproducto AND estado not like '0'", nativeQuery = true)
    List<Object[]> findNotaDtosByProductoIdAndEstadoNot(@Param("idproducto") int idproducto);

}

package com.oscarchavez.smc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarchavez.smc.dto.NotaDto;
import com.oscarchavez.smc.model.Nota;
import com.oscarchavez.smc.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    public List<NotaDto> listaNotas(int idproducto) {

        List<Object[]> results = notaRepository.findNotaDtosByProductoIdAndEstadoNot(idproducto);

        List<NotaDto> notas = new ArrayList<>();

    for (Object[] result : results) {
        NotaDto notaDto = new NotaDto(
            (Integer) result[0],  // idnota
            (String) result[1],   // descripcion
            (String) result[2],   // estado
            (Date) result[3],   // fecha
            (Integer) result[4]   // idproducto
        );
        notas.add(notaDto);
    }

    return notas;

    }

    public Nota inserNota(Nota nota) {
        return notaRepository.save(nota);
    }

    public Nota updateNota(int idnota, String estado) {

        Nota notaUpdate = notaRepository.findById(idnota).orElse(null);
        notaUpdate.setEstado(estado);

        return notaRepository.save(notaUpdate);
    }

}

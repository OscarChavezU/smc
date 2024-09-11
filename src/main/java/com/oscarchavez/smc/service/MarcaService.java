package com.oscarchavez.smc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarchavez.smc.model.Marca;
import com.oscarchavez.smc.repository.MarcaRepository;



@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listarMarcas(String estado) {
        return marcaRepository.listaMarcas(estado);
    }

}

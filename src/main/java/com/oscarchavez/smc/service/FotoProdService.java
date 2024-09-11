package com.oscarchavez.smc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscarchavez.smc.model.FotoProd;
import com.oscarchavez.smc.repository.FotoProdRepository;

@Service
public class FotoProdService {

    @Autowired
    private FotoProdRepository fotoProdRepository;

    public FotoProd createFotoProd() {
        FotoProd fotoProd = new FotoProd();
        fotoProd.setUrlimagen("");  // Dejar vacío
        fotoProd.setImagen(null);   // Dejar vacío
        return fotoProdRepository.save(fotoProd);
    }

    public FotoProd updateFotoProdUrl(Integer id, String imageUrl) {
        Optional<FotoProd> optionalFotoProd = fotoProdRepository.findById(id);
        if (optionalFotoProd.isPresent()) {
            FotoProd fotoProd = optionalFotoProd.get();
            fotoProd.setUrlimagen(imageUrl);
            return fotoProdRepository.save(fotoProd);
        }
        return null;  // Manejar el caso de que no exista el registro
    }
}

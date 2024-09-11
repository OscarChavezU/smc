package com.oscarchavez.smc.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.carpeta}")
    private String carpeta;

    public String uploadFile(MultipartFile file, int idfoto) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", carpeta ,
            "public_id", idfoto // Especifica la carpeta
        ));
        return uploadResult.get("url").toString();  // Obtiene la URL de la imagen cargada
    }
}
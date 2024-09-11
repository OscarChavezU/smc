package com.oscarchavez.smc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "fotosprod")
@Data
@NoArgsConstructor
public class FotoProd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idfoto;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    private String urlimagen;

    @OneToOne(mappedBy = "fotoProd",fetch = FetchType.LAZY)
    private Producto producto;
}
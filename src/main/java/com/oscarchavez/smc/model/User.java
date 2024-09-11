package com.oscarchavez.smc.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario"})})
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int id;

    @Column(name = "usuario", nullable = false, unique = true)
    private String usuario;

    @Column(name = "password1",nullable = false)
    private String password;
    
    @Column(name = "areaacceso",nullable = false)
    private String areaacceso;

    @Column(name = "estado",nullable = false)
    private String estado;

    @Column(name = "idsucursal",nullable = false)
    private int idsucursal;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.areaacceso));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public String getUsername() {
        return this.usuario; // Devuelve el nombre de usuario
    }

    
    

    // Getters y Setters
}
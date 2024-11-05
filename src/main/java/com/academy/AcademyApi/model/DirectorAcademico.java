package com.academy.AcademyApi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString
@Table(name = "directores_academicos", schema = "ACADEMY")
public class DirectorAcademico {

    @Id
    @Column(name = "id", nullable = false)
    private Long id; // Cedula

    @Column(name = "full_name", length = 60, nullable = false)
    private String fullName;

    @Column(name = "email", length = 180, nullable = false)
    private String email;

    @Lob
    @Column(name = "profile_pic")
    private byte[] profilePic;
}

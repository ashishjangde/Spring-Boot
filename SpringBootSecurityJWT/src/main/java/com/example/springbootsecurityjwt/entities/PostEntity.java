package com.example.springbootsecurityjwt.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "postservices")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
}
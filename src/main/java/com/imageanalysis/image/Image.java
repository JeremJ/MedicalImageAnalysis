package com.imageanalysis.image;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "image")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] file;
    private String fileName;
    private Instant date;
    private Long size;
    private String fileExtension;
    private Double diseaseProbability;
}

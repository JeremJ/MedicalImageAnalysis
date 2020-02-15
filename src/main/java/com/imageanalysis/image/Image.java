package com.imageanalysis.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "image", schema = "public")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] file;
    private String fileName;
    @CreationTimestamp
    private Instant date;
    private Long size;
    private String fileExtension;
    private Double diseaseProbability;

    public Image(byte[] file, String fileName, Long size, String fileExtension) {
        this.file = file;
        this.fileName = fileName;
        this.size = size;
        this.fileExtension = fileExtension;
    }
}

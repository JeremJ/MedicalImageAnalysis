package com.imageanalysis.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imageanalysis.user.image.UserImage;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Table(name = "image", schema = "public")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "images")
@Builder(toBuilder = true)
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
    @JsonIgnore
    @OneToMany(mappedBy = "image", cascade = ALL, orphanRemoval = true)
    private Set<UserImage> images = new HashSet<>();

    public Image(byte[] file, String fileName, Long size, String fileExtension) {
        this.file = file;
        this.fileName = fileName;
        this.size = size;
        this.fileExtension = fileExtension;
    }
}

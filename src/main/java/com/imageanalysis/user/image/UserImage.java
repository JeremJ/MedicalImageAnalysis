package com.imageanalysis.user.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imageanalysis.image.Image;
import com.imageanalysis.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_image", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String description;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonIgnore
    private Image image;

    public UserImage(User user, Image image, String description) {
        this.user = user;
        this.image = image;
        this.description = description;
    }
}

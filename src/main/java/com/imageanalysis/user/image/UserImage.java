package com.imageanalysis.user.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imageanalysis.image.Image;
import com.imageanalysis.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_image")
@AllArgsConstructor
@NoArgsConstructor
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
}

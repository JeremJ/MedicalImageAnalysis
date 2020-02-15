package com.imageanalysis.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {

    @NotEmpty(message = "Description is required.")
    private String description;
}

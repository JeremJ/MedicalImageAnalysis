package com.imageanalysis.user.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImagePageDTO {
    private Integer totalPages;
    private List<UserImageDTO> images;
}

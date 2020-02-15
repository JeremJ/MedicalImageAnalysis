package com.imageanalysis.user.image;

import com.imageanalysis.image.ImageExtendedDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImageDTO {
    private Long id;
    private ImageExtendedDTO image;
}

package com.imageanalysis.user.image;

import com.imageanalysis.image.ImageMapper;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, uses = ImageMapper.class)
public interface UserImageMapper {

    List<UserImageDTO> toUserImageDTOs(List<UserImage> userImages);

    UserImageDTO toUserImageDTO(UserImage userImage);
}

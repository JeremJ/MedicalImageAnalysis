package com.imageanalysis.user.image;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    List<UserImage> findAllByUserId(Long userId, Pageable pageable);

    UserImage findByIdAndUserId(Long imageId, Long userId);
}

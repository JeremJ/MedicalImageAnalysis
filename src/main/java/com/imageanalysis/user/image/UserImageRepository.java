package com.imageanalysis.user.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    Page<UserImage> findAllByUserId(Long userId, Pageable pageable);

    UserImage findByIdAndUserId(Long imageId, Long userId);
}

package com.imageanalysis.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicPageDTO {
    private Integer totalPages;
    private List<UserBasicDTO> users;
}

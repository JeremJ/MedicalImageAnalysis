package com.imageanalysis.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserBasicPageDTO {
    private Long totalElements;
    private List<UserBasicDTO> users;
}

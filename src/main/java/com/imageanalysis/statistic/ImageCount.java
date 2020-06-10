package com.imageanalysis.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ImageCount {

    private Date date;
    private Long amount;
}

package com.imageanalysis.image;

import com.imageanalysis.statistic.ImageCount;

import java.util.List;

public interface CustomizedImageRepository {
    List<ImageCount> countImagesByDay();
}

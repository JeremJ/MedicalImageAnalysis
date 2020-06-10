package com.imageanalysis.image;

import com.imageanalysis.statistic.ImageCount;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;

public class CustomizedImageRepositoryImpl implements CustomizedImageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ImageCount> countImagesByDay() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ImageCount> cq = cb.createQuery(ImageCount.class);
        Root<Image> imageRoot = cq.from(Image.class);

        cq.multiselect(imageRoot.get(Image_.date).as(Date.class), cb.count(imageRoot));
        cq.groupBy(imageRoot.get(Image_.date).as(Date.class));

        cq.orderBy(cb.asc(imageRoot.get(Image_.date).as(Date.class)));
        return entityManager.createQuery(cq).getResultList();
    }
}

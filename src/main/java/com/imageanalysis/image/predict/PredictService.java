package com.imageanalysis.image.predict;

import com.imageanalysis.exception.ProcessingFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class PredictService {

    public static final String PREDICTION_API = "http://localhost:8000/api/v1/predictions";
    public Double predict(MultipartFile multipartFile) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Double> exchange = restTemplate
                    .postForEntity(PREDICTION_API, multipartFile.getBytes(), Double.class);
            return exchange.getBody();
        } catch (IOException e) {
            throw new ProcessingFileException("dupa");
        }
    }


}

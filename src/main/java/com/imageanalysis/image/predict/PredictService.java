package com.imageanalysis.image.predict;

import com.imageanalysis.exception.ProcessingFileException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.util.Base64.*;
import static java.util.Objects.*;

@Service
public class PredictService {

    static final String PREDICTION_API = "http://localhost:8000/predict";

    public Double invokePredictionApi(MultipartFile multipartFile) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            map.add("file", getEncoder().encodeToString(multipartFile.getBytes()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(
                    map, headers);
            ProbabilityResponse response = restTemplate.postForObject(PREDICTION_API, requestEntity, ProbabilityResponse.class);
            return requireNonNull(response).getProbabilityHealthy();
        } catch (ProcessingFileException | IOException e) {
            throw new ProcessingFileException("cannot fetch prediction");
        }
    }


}

package com.imageanalysis.image.predict;

import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.deeplearning4j.nn.modelimport.keras.KerasModelImport.importKerasSequentialModelAndWeights;

@Service
public class PredictService {
    public static final String MODEL_FILE_CLASSPATH = "classpath:Lung-model.h5";

    private Resource modelFile;

    public PredictService(@Value(MODEL_FILE_CLASSPATH) Resource modelFile) {
        this.modelFile = modelFile;
    }

    public Double predict(MultipartFile multipartFile) {
        var result = 0d;
        try {
            var model = importKerasSequentialModelAndWeights(modelFile.getInputStream(), true);
            INDArray ads = new ImageLoader().asMatrix(multipartFile.getInputStream());
            var ss = model.output(ads);
            result = 2d;
        } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        }
        return result;
    }


}

package com.imageanalysis.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProcessingFileException extends RuntimeException {
    private final String message;
}

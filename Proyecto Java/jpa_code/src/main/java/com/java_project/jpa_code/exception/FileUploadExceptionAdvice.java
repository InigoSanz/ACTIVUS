package com.java_project.jpa_code.exception;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clase que maneja las excepciones de subida de archivos.
 * Se encarga de manejar la excepción de subida de archivos demasiado grandes.
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e) {
        model.addAttribute("message", "File is too large!");
        return "upload_form";
    }
}
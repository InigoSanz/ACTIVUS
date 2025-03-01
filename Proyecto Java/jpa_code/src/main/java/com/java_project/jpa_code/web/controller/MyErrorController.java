package com.java_project.jpa_code.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Custom error controller
 * @author Baeldung
 * <a href="https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-basic-customization/src/main/resources/templates">...</a>
 */
@Controller
public class MyErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/error-500";
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return "error/error-405";
            }
        }
        return "error/errorGenerico";
    }
}
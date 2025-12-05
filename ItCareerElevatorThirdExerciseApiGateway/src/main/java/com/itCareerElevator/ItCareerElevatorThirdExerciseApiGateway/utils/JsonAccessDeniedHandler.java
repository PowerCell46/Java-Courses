package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpServletResponse.SC_FORBIDDEN,
                "You don't have permissions to access this resource.",
                System.currentTimeMillis()
        );

        objectMapper.writeValue(res.getWriter(), errorResponse);
    }
}

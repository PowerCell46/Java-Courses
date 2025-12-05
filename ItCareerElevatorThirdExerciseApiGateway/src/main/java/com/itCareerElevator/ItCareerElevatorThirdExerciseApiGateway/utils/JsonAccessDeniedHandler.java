package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException, ServletException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        res.setContentType("application/json");
        long now = System.currentTimeMillis();

        res.getWriter().write(
                "{\"status\":403," +
                        "\"message\":\"You don't have permissions to access this endpoint.\"," +
                        "\"timestamp\":" + now + "}"
        );
    }
}

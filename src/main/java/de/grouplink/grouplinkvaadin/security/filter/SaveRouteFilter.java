package de.grouplink.grouplinkvaadin.security.filter;//package com.naxit.arbiana.security.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class SaveRouteFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(
//                !request.getRequestURI().startsWith("/VAADIN") &&
//                        !request.getRequestURI().startsWith("/images") &&
//                        !request.getRequestURI().startsWith("/assets") &&
//                        !request.getRequestURI().startsWith("/sw") &&
//                        !request.getRequestURI().startsWith("/login") &&
//                        !request.getRequestURI().startsWith("/favicon") &&
//                        !request.getRequestURI().equals("/") &&
//                        !request.getRequestURI().equals("/logout")
//        ) {
//            request.getSession().setAttribute("lastRoute", request.getRequestURI());
//        }
//        filterChain.doFilter(request, response);
//    }
//}

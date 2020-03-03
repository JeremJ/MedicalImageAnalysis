package com.imageanalysis.security;

import com.imageanalysis.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Slf4j
@RequiredArgsConstructor
public class SuccessfulAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            userService.createUserIfNotExists(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

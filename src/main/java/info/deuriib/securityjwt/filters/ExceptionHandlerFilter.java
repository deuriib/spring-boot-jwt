package info.deuriib.securityjwt.filters;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    
    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e);
            logger.error(e.getMessage(), e);
        } catch (RuntimeException | ServletException e) {
            logger.error(e.getMessage(), e);
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
        response.setStatus(status.value());
        response.setContentType("application/json");

        ApiError apiError = new ApiError(status, ex);
        try {
            String json = apiError.convertToJson();
            logger.debug(json);
            response.getWriter().write(json);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
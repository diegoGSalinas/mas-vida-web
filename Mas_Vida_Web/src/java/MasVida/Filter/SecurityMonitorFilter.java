package MasVida.Filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import MasVida.Log.LoggerUtil;

public class SecurityMonitorFilter implements Filter {
    private static final Logger logger = LoggerUtil.getLogger(SecurityMonitorFilter.class.getName());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("SecurityMonitorFilter inicializado");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Monitoreo de acceso
        logger.info(String.format("Acceso a %s - IP: %s",
                httpRequest.getRequestURI(),
                httpRequest.getRemoteAddr()));
        
        // Monitoreo de tiempo de respuesta
        long startTime = System.currentTimeMillis();
        
        chain.doFilter(request, response);
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        if (responseTime > 1000) { // Si la respuesta toma más de 1 segundo
            logger.warning(String.format("Tiempo de respuesta alto: %dms - URI: %s",
                    responseTime, httpRequest.getRequestURI()));
        }
        
        logger.info(String.format("Tiempo de respuesta: %dms - URI: %s",
                responseTime, httpRequest.getRequestURI()));
    }
    
    @Override
    public void destroy() {
        logger.info("SecurityMonitorFilter destruido");
    }
}

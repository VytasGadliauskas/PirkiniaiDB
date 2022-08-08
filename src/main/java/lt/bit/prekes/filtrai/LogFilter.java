package lt.bit.prekes.filtrai;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(filterName = "LogFilter", urlPatterns = {"/*"})
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      //  System.out.println("Request  start -------------------------");
      //  System.out.println("Request  remote ip:"+servletRequest.getRemoteAddr());
        System.out.println("Request  :"+((HttpServletRequest) servletRequest).getRequestURI());
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        Enumeration<String> parameterNames = httpRequest.getParameterNames();
      //  if (headerNames != null) {
      //      while (headerNames.hasMoreElements()) {
      //         System.out.println("Header: " + httpRequest.getHeader(headerNames.nextElement()));
      //      }
      //  }
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String parametras = parameterNames.nextElement();
                System.out.println("Parameter: "+parametras+"=" + httpRequest.getParameter(parametras));
            }
        }
        System.out.println("");

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}

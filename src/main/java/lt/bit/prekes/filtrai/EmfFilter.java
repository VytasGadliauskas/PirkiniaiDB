package lt.bit.prekes.filtrai;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "EmfFilter", urlPatterns = {"/*"})
public class EmfFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletContext sc = servletRequest.getServletContext();
        EntityManagerFactory emf = (EntityManagerFactory) sc.getAttribute("emf");
        EntityManager em = null;
        try{
            em = emf.createEntityManager();
            servletRequest.setAttribute("em",em);
            filterChain.doFilter(servletRequest,servletResponse);
        } finally {
            if (em != null) {
               em.close();
            }
        }
    }

    @Override
    public void destroy() {

    }
}

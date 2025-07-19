package pt.isep.splitix.usermanagement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import pt.isep.splitix.domain.User;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Permitir acesso livre a páginas públicas
        if (uri.startsWith("/login") || uri.startsWith("/register") || uri.startsWith("/css") || uri.startsWith("/js")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return true; // Utilizador autenticado
        }

        response.sendRedirect("/login");
        return false;
    }
}


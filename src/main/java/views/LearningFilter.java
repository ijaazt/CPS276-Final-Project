package views;

import info.ServletInfo;
import views.model.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LearningFilter", urlPatterns = "/learning")
public class LearningFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String forUrl;
        Authentication authentication = (Authentication) req.getSession().getAttribute(ServletInfo.USER_AUTH.info());
        if(authentication != null) {
            chain.doFilter(req, res);
        } else {
            forUrl = "signin";
            res.sendRedirect(forUrl);
        }
    }
}

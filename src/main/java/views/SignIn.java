package views;

import controller.Repository;
import exceptions.NoUserFound;
import info.ServletInfo;
import views.model.Authentication;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignIn", urlPatterns = "/signin")
public class SignIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/signin.jsp");
        dispatcher.forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String redirectUrl = "learning";
        if(!"".equals(username) && !"".equals(password)) {
            Repository repository = (Repository) getServletContext().getAttribute(ServletInfo.REPOSITORY.info());
            try {
                Authentication authentication = new Authentication(repository.getUser(username, password));
                req.getSession().setAttribute(ServletInfo.USER_AUTH.info(), authentication);
            } catch (NoUserFound noUserFound) {
                noUserFound.printStackTrace();
                redirectUrl = "signup";
            }
            resp.sendRedirect(redirectUrl);
        } else {
            resp.sendRedirect("signin");
        }
    }
}

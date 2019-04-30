package views;

import controller.Repository;
import exceptions.NoUserFound;
import info.ServletInfo;
import model.User;
import views.model.Authentication;
import views.model.UserParameters;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignUp", urlPatterns = "/signup")
public class SignUp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/signup.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Repository repository = (Repository) getServletContext().getAttribute(ServletInfo.REPOSITORY.info());
            UserParameters userParameters = new UserParameters(req.getParameterMap());
            Authentication authentication;
            String password =  userParameters.getParameterValue().getPassword();
            String username =  userParameters.getParameterValue().getUserName();
            repository.addUser(userParameters.getParameterValue());
            User repoUser = repository.getUser(username, password);
            authentication = new Authentication(repoUser);
            req.getSession().setAttribute(ServletInfo.USER_AUTH.info(), authentication);
            resp.sendRedirect("learning");
        } catch (Exception | NoUserFound e) {
            e.printStackTrace();
        }
    }
}

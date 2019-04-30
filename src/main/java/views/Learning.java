package views;

import controller.Repository;
import exceptions.NoUserFound;
import info.Method;
import info.ServletInfo;
import views.model.Authentication;
import views.model.LearningParameters;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Learning", urlPatterns = "/learning")
public class Learning extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository repository = (Repository) getServletContext().getAttribute(ServletInfo.REPOSITORY.info());
        String method = request.getParameter("method");
        LearningParameters learningParameters;
        try {
            learningParameters = new LearningParameters(request.getParameterMap(), Method.valueOf(method));
            Authentication authentication = (Authentication) request.getAttribute(ServletInfo.USER_AUTH.info());
            switch (learningParameters.getMethod()) {
                case DELETE_ALL:
                    repository.deleteLearning(null, authentication);
                    break;
                case PUT:
                    repository.modifyLearning(authentication, learningParameters.getParameterValue());
                    break;
                case DELETE:
                    repository.deleteLearning(learningParameters.getParameterValue().getId(), authentication);
                    break;
                case GET:
                    break;
                case POST:
                    repository.addLearning(authentication, learningParameters.getParameterValue());
            }
        } catch (Exception | NoUserFound e) {
            e.printStackTrace();
        }
        forward(request, response, repository);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, Repository repository) throws ServletException, IOException {
        Authentication authentication = (Authentication) request.getSession().getAttribute(ServletInfo.USER_AUTH.info());
        try {
            request.setAttribute("learningList", repository.getLearning(authentication));
        } catch (NoUserFound noUserFound) {
            noUserFound.printStackTrace();
        }
        request.setAttribute("title", "Welcome to Learning");
        request.setAttribute("userId", 1);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showLearning.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository repository = (Repository) getServletContext().getAttribute(ServletInfo.REPOSITORY.info());
        forward(request, response, repository);
    }
}

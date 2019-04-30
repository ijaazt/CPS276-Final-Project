package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import info.Method;
import model.User;

import java.util.Map;

public class UserParameters extends Parameters<User> {
    public UserParameters(Map<String, String[]> parameters) throws Exception {
        super(parameters);
    }

    public UserParameters(Map<String, String[]> parameters, Method method) throws Exception {
        super(parameters, method);
    }
    @Override
    public User getParameterValue() throws NullParameterException, NullArgumentException {
        String userName = getParameterArg("username", getParameters());
        String password = getParameterArg("password", getParameters());
        String lastName = getParameterArg("lastName", getParameters());
        String firstName = getParameterArg("firstName", getParameters());
        Integer id = Integer.valueOf(getParameterArg("id", getParameters()));
        //public User(String userName, String password, String firstName, String lastName, Integer id) {
        return new User(userName, password, firstName, lastName, id);
    }
}

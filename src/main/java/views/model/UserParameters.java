package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import model.User;

import java.util.Map;

class UserParameters extends ModelParameters<User>{
    UserParameters(Map<String, String> parameters) throws Exception {
        super(parameters);
    }

    @Override
    User getParameterValue() throws NullParameterException, NullArgumentException {
        String userName = getParameterArg("userName", getParemeters());
        String password = getParameterArg("password", getParemeters());
        String lastName = getParameterArg("lastName", getParemeters());
        String firstName = getParameterArg("firstName", getParemeters());
        Integer id = Integer.valueOf(getParameterArg("id", getParemeters()));
        //public User(String userName, String password, String firstName, String lastName, Integer id) {
        return new User(userName, password, firstName, lastName, id);
    }
}

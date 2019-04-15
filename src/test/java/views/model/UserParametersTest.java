package views.model;

import model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UserParametersTest {
    @Test
    public void userParameter() throws Exception {
        Map<String, String> stringMap  = new HashMap<>();
        User user = new User("ijaaz", "aaadkfjasdlfk", "Muhammad", "Tello", 1);
        stringMap.put("userName", user.getUserName());
        stringMap.put("password", user.getPassword());
        stringMap.put("firstName", user.getFirstName());
        stringMap.put("lastName", user.getLastName());
        stringMap.put("id", "1");
        UserParameters userParameters = new UserParameters(stringMap);
        assertEquals(user, userParameters.getParameterValue());
    }
}
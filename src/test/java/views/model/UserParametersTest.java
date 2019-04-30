package views.model;

import model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserParametersTest {
    @Test
    void userParameter() throws Exception {
        Map<String, String[]> stringMap  = new HashMap<>();
        User user = new User("ijaaz", "aaadkfjasdlfk", "Muhammad", "Tello", 1);
        stringMap.put("username", new String[]{user.getUserName()});
        stringMap.put("password", new String[]{user.getPassword()});
        stringMap.put("firstName", new String[]{user.getFirstName()});
        stringMap.put("lastName", new String[]{user.getLastName()});
        stringMap.put("id", new String[]{"1"});
        UserParameters userParameters = new UserParameters(stringMap);
        assertEquals(user, userParameters.getParameterValue());
    }
}
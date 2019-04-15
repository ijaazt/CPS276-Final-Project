package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import model.Learning;

import java.time.LocalDate;
import java.util.Map;

public class LearningParameters extends ModelParameters<Learning> {

    LearningParameters(Map<String, String> parameters) throws Exception {
        super(parameters);
    }

    @Override
    Learning getParameterValue() throws NullParameterException, NullArgumentException {
        //public Learning(String category, String learning, LocalDate date, Integer id, int userId) {
        String category = getParameterArg("category", getParemeters());
        String learning = getParameterArg("learning", getParemeters());
        String date = getParameterArg("date", getParemeters());
        String id = getParameterArg("id", getParemeters());
        String userId = getParameterArg("userId", getParemeters());
        return new Learning(category, learning, LocalDate.parse(date), Integer.valueOf(id), Integer.valueOf(userId));
    }
}

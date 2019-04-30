package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import info.Method;
import model.Learning;

import java.time.LocalDate;
import java.util.Map;

public class LearningParameters extends Parameters<Learning> {

    public LearningParameters(Map<String, String[]> parameters) throws Exception {
        super(parameters);
    }

    public LearningParameters(Map<String, String[]> parameterMap, Method valueOf) throws Exception {
        super(parameterMap, valueOf);
    }

    @Override
    public Learning getParameterValue() throws NullParameterException, NullArgumentException {
        //public Learning(String category, String learning, LocalDate date, Integer id, int userId) {
        String category = getParameterArg("category", getParameters());
        String learning = getParameterArg("learning", getParameters());
        String id, userId, date;
        userId = getParameterArg("userId", getParameters());
        try {
            date = getParameterArg("date", getParameters());
            id = getParameterArg("id", getParameters());
        } catch (NullParameterException e) {
            System.out.println(e + " setting: id to null ");
            return new Learning(category, learning, LocalDate.now(), null, Integer.parseInt(userId));
        }

        return new Learning(category, learning, LocalDate.parse(date), Integer.valueOf(id), Integer.valueOf(userId));
    }
}

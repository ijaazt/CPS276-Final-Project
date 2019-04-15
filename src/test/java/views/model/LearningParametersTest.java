package views.model;

import model.Learning;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LearningParametersTest {
    @Test
    public void testParameters() throws Exception {
        Learning learning = new Learning("cat", "learni", LocalDate.parse("2015-01-22"), 1, 1);
        Map<String, String> learningMap = new HashMap<>();
        learningMap.put("category", learning.getCategory());
        learningMap.put("learning", learning.getLearning());
        learningMap.put("date", "2015-01-22");
        learningMap.put("id", "1");
        learningMap.put("userId", "1");
        LearningParameters parameters = new LearningParameters(learningMap);
        assertEquals(learning, parameters.getParameterValue());
    }

}
package views.model;

import model.Learning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LearningParametersTest {
    @Test
    void testParameters() throws Exception {
        Learning learning = new Learning("cat", "learni", LocalDate.parse("2015-01-22"), 1, 1);
        Map<String, String[]> learningMap = new HashMap<>();
        learningMap.put("category", new String[]{learning.getCategory()});
        learningMap.put("learning", new String[]{learning.getLearning()});
        learningMap.put("date", new String[]{"2015-01-22"});
        learningMap.put("id", new String[]{"1"});
        learningMap.put("userId", new String[]{"1"});
        LearningParameters parameters = new LearningParameters(learningMap);
        assertEquals(learning, parameters.getParameterValue());
    }

}
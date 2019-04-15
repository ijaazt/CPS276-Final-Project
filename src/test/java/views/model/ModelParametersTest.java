package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ModelParametersTest {
    ModelParameters<Object> modelParameters;

    @Before
    public void setUp() throws Exception {
        modelParameters = new ModelParameters<Object>(Collections.singletonMap("Hello", "World")) {
            @Override
            Object getParameterValue() {
                return null;
            }
        };
    }

    @Test
    public void testParameters() throws NullParameterException, NullArgumentException {
        String model = modelParameters.getParameterArg("Hello", Collections.singletonMap("Hello", "World"));
        assertEquals("World", model);
    }
}
package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {
    Parameters<Object> modelParameters;

    @BeforeEach
    void setUp() throws Exception {
        modelParameters = new Parameters<Object>(Collections.singletonMap("Hello", new String[]{"World"})) {
            @Override
            public Object getParameterValue() {
                return null;
            }
        };
    }

    @Test
    void testParameters() throws NullParameterException, NullArgumentException {
        String model = modelParameters.getParameterArg("Hello", Collections.singletonMap("Hello", new String[]{"World"}));
        assertEquals("World", model);
    }
}
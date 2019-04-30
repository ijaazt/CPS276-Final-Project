package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import info.Method;

import java.util.Map;

public abstract class Parameters<E> {
    private final Map<String, String[]> paremeters;
    private Method method;

    Parameters(Map<String, String[]> parameters, Method method) throws Exception {
        this.paremeters = parameters;
        this.method = method;
    }
    Parameters(Map<String, String[]> parameters) throws Exception {
        this.paremeters = parameters;
    }

    String getParameterArg(String paramName, Map<String, String[]> parameters) throws NullParameterException, NullArgumentException {
        if(paramName == null) throw new NullArgumentException("parameter paramName is null");
        String paramValue = parameters.get(paramName)[0];
        if(paramValue.equals("")) {
            throw new NullParameterException(paramName);
        }
        return paramValue;
    }

    public abstract E getParameterValue() throws NullParameterException, NullArgumentException;

    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }

    Map<String, String[]> getParameters() {
        return paremeters;
    }
}

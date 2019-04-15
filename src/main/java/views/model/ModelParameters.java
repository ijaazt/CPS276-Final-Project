package views.model;

import exceptions.NullArgumentException;
import exceptions.NullParameterException;
import info.Method;

import java.util.Map;

abstract class ModelParameters<E> {
    private final Map<String, String> paremeters;
    private Method method;

    ModelParameters(Map<String, String> parameters) throws Exception {
        this.paremeters = parameters;
    }

    String getParameterArg(String paramName, Map<String, String> parameters) throws NullParameterException, NullArgumentException {
        if(paramName == null) throw new NullArgumentException("parameter paramName is null");
        String paramValue = parameters.get(paramName);
        if(paramValue.equals("")) {
            throw new NullParameterException(paramName);
        }
        return paramValue;
    }

    abstract E getParameterValue() throws NullParameterException, NullArgumentException;

    Method getMethod() {
        return method;
    }
    void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, String> getParemeters() {
        return paremeters;
    }
}

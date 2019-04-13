package exceptions;

public class ConnectionAttributeIsNull extends Exception {
    public ConnectionAttributeIsNull(String attribute) {
        super(attribute);
    }
}

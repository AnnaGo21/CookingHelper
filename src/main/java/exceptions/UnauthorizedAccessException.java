package exceptions;

public class UnauthorizedAccessException extends Throwable {
    public UnauthorizedAccessException(String name) {
        System.out.println("This: " + name + ", can not access to this method" );
    }
}

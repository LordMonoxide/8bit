package lordmonoxide.bit;

public class ConnectionMismatchException extends RuntimeException {
  public ConnectionMismatchException() {

  }

  public ConnectionMismatchException(final String message) {
    super(message);
  }

  public ConnectionMismatchException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ConnectionMismatchException(final Throwable cause) {
    super(cause);
  }

  protected ConnectionMismatchException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

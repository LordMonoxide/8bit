package lordmonoxide.bit;

public class InvalidBusStateException extends RuntimeException {
  public InvalidBusStateException() {

  }

  public InvalidBusStateException(final String message) {
    super(message);
  }

  public InvalidBusStateException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidBusStateException(final Throwable cause) {
    super(cause);
  }

  protected InvalidBusStateException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

package lordmonoxide.bit;

public class InvalidConnectionValueException extends RuntimeException {
  public InvalidConnectionValueException() {

  }

  public InvalidConnectionValueException(final String message) {
    super(message);
  }

  public InvalidConnectionValueException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidConnectionValueException(final Throwable cause) {
    super(cause);
  }

  protected InvalidConnectionValueException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

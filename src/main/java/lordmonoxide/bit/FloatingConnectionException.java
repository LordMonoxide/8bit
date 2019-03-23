package lordmonoxide.bit;

public class FloatingConnectionException extends RuntimeException {
  public FloatingConnectionException() {

  }

  public FloatingConnectionException(final String message) {
    super(message);
  }

  public FloatingConnectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FloatingConnectionException(final Throwable cause) {
    super(cause);
  }

  protected FloatingConnectionException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

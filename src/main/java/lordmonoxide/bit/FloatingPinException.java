package lordmonoxide.bit;

public class FloatingPinException extends RuntimeException {
  public FloatingPinException() {

  }

  public FloatingPinException(final String message) {
    super(message);
  }

  public FloatingPinException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public FloatingPinException(final Throwable cause) {
    super(cause);
  }

  protected FloatingPinException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

package lordmonoxide.bit.parts;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.InvalidConnectionValueException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class OutputConnection extends Connection {
  private static boolean enableStateCallbacks = true;

  public static void enableStateCallbacks() {
    enableStateCallbacks = true;
  }

  public static void disableStateCallbacks() {
    enableStateCallbacks = false;
  }

  private final Map<InputConnection, Consumer<OptionalInt>> onStateChange = new LinkedHashMap<>();

  private final int max;
  private int value;
  private boolean disabled;

  public OutputConnection(final int size) {
    super(size);
    this.max = (int)Math.pow(2, size);
  }

  public OutputConnection onStateChange(final InputConnection connection, final Consumer<OptionalInt> onStateChange) {
    this.onStateChange.put(connection, onStateChange);

    if(enableStateCallbacks) {
      onStateChange.accept(this.getValue());
    }

    return this;
  }

  @Override
  public OptionalInt getValue() {
    if(this.disabled) {
      return OptionalInt.empty();
    }

    return OptionalInt.of(this.value);
  }

  public OutputConnection disable() {
    this.disabled = true;

    if(enableStateCallbacks) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getValue()));
    }

    return this;
  }

  public OutputConnection enable() {
    this.disabled = false;

    if(enableStateCallbacks) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getValue()));
    }

    return this;
  }

  public OutputConnection setValue(final int value) {
    if(value > this.max) {
      throw new InvalidConnectionValueException();
    }

    this.value = value;

    if(!this.disabled && enableStateCallbacks) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getValue()));
    }

    return this;
  }

  @Override
  public String toString() {
    if(this.getValue().isEmpty()) {
      throw new FloatingConnectionException();
    }

    return "Output [" + this.getValue() + ']';
  }
}

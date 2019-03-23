package lordmonoxide.bit.parts;

import lordmonoxide.bit.ConnectionMismatchException;
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

  public static OutputConnection widen(final int size, final OutputConnection source) {
    return new OutputWidenerConnection(size, source);
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

  public static class OutputWidenerConnection extends OutputConnection {
    private final OutputConnection source;

    public OutputWidenerConnection(final int size, final OutputConnection source) {
      super(size);

      if(source.size >= size) {
        throw new ConnectionMismatchException("Source size must be smaller than widened size");
      }

      this.source = source;
    }

    @Override
    public OutputConnection onStateChange(final InputConnection connection, final Consumer<OptionalInt> onStateChange) {
      return this.source.onStateChange(connection, onStateChange);
    }

    @Override
    public OptionalInt getValue() {
      if(this.source.getValue().isEmpty()) {
        return OptionalInt.empty();
      }

      int value = 0;

      for(int newBit = 0; newBit < this.size; newBit++) {
        final int oldBit = newBit % this.source.size;

        value |= ((this.source.value >> oldBit) & 0b1) << newBit;
      }

      return OptionalInt.of(value);
    }

    @Override
    public OutputConnection disable() {
      throw new RuntimeException("Can't disable widened pin");
    }

    @Override
    public OutputConnection enable() {
      throw new RuntimeException("Can't enable widened pin");
    }

    @Override
    public OutputConnection setValue(final int value) {
      throw new RuntimeException("Can't set value of widened pin");
    }

    @Override
    public String toString() {
      return super.toString();
    }
  }
}

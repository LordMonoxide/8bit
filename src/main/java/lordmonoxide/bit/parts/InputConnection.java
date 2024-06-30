package lordmonoxide.bit.parts;

import lordmonoxide.bit.ConnectionMismatchException;
import lordmonoxide.bit.FloatingConnectionException;

import javax.annotation.Nullable;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class InputConnection extends Connection {
  @Nullable
  private OutputConnection connection;

  private final Consumer<OptionalInt> onStateChange;

  public static InputConnection aggregate(final int size, final InputConnection... inputs) {
    return new AggregateInputConnection(size, inputs);
  }

  public static InputConnection shrinker(final int size, final InputConnection original) {
    return new InputShrinkerConnection(size, original);
  }

  public InputConnection(final int bits) {
    this(bits, value -> { });
  }

  public InputConnection(final int bits, final Consumer<OptionalInt> onStateChange) {
    super(bits);
    this.onStateChange = onStateChange;
  }

  public InputConnection connectTo(final OutputConnection connection) {
    if(this.bits != connection.bits) {
      throw new ConnectionMismatchException("Attempted to connect " + this.bits + " pin input to " + connection.bits + " output");
    }

    this.connection = connection;
    connection.onStateChange(this, this.onStateChange);

    return this;
  }

  @Override
  public OptionalInt getValue() {
    if(this.connection == null) {
      return OptionalInt.empty();
    }

    return this.connection.getValue();
  }

  @Override
  public String toString() {
    if(this.getValue().isEmpty()) {
      return "Input [disconnected]";
    }

    return "Input [" + this.getValue().getAsInt() + ']';
  }

  public static final class AggregateInputConnection extends InputConnection {
    private final InputConnection[] connections;

    private AggregateInputConnection(final int bits, final InputConnection... connections) {
      super(bits);
      this.connections = connections;
    }

    @Override
    public InputConnection connectTo(final OutputConnection output) {
      for(final InputConnection connection : this.connections) {
        connection.connectTo(output);
      }

      return this;
    }

    @Override
    public OptionalInt getValue() {
      int output = 0;

      for(final InputConnection connection : this.connections) {
        if(connection.getValue().isEmpty()) {
          throw new FloatingConnectionException("Aggregate input was floating");
        }

        output |= connection.getValue().getAsInt();
      }

      return OptionalInt.of(output);
    }
  }

  public static class InputShrinkerConnection extends InputConnection {
    private final InputConnection original;

    public InputShrinkerConnection(final int bits, final InputConnection original) {
      super(bits, state -> { });

      if(original.bits <= bits) {
        throw new ConnectionMismatchException("Original bits must be larger than shrunk bits");
      }

      this.original = original;
    }

    @Override
    public InputConnection connectTo(final OutputConnection connection) {
      return this.original.connectTo(new OutputConnection.OutputWidenerConnection(this.original.bits, connection));
    }

    @Override
    public OptionalInt getValue() {
      return this.original.getValue();
    }
  }
}

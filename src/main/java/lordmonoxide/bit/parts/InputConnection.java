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

  public InputConnection(final int size) {
    this(size, value -> { });
  }

  public InputConnection(final int size, final Consumer<OptionalInt> onStateChange) {
    super(size);
    this.onStateChange = onStateChange;
  }

  public InputConnection connectTo(final OutputConnection connection) {
    if(this.size < connection.size) {
      throw new ConnectionMismatchException("Attempted to connect " + this.size + " pin input to " + connection.size + " output");
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

    private AggregateInputConnection(final int size, final InputConnection... connections) {
      super(size);
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
}

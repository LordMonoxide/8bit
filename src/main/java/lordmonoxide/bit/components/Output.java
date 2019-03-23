package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;

import java.util.OptionalInt;

public class Output extends Component {
  private final Register register;

  public final InputConnection in;

  public final InputConnection load;
  public final InputConnection clock;

  public final int size;

  public Output(final int size) {
    this.size = size;
    this.register = new Register(size);
    this.in = this.register.in;
    this.load = this.register.load;
    this.clock = InputConnection.aggregate(size, this.register.clock, new InputConnection(1, this::onClock));
  }

  public void clear() {
    this.register.clear();
  }

  private void onClock(final OptionalInt value) {
    if(value.getAsInt() != 0 && this.load.getValue().orElseThrow(() -> new FloatingConnectionException("Output load is floating")) != 0) {
      System.out.println("Output: " + this.in.getValue().orElseThrow(() -> new FloatingConnectionException("Output in is floating")));
    }
  }
}

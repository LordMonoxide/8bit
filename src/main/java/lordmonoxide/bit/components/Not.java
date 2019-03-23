package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;

import java.util.OptionalInt;

public class Not extends Component {
  public final InputConnection in;
  public final OutputConnection out;

  public Not(final int size) {
    this.in = new InputConnection(size, this::onStateChange);
    this.out = new OutputConnection(size).setValue(0);
  }

  private void onStateChange(final OptionalInt value) {
    this.out.setValue(~value.orElseThrow(() -> new FloatingConnectionException("Not in is floating")));
  }

  @Override
  public String toString() {
    return "Not [" + this.in.getValue() + " ~ " + this.out.getValue() + ']';
  }
}

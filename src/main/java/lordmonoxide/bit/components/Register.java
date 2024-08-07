package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;
import lordmonoxide.bit.parts.Pins;

import java.util.OptionalInt;

public class Register extends Component {
  public final InputConnection in;
  public final OutputConnection out;

  public final InputConnection load = new InputConnection(1);
  public final InputConnection clock = new InputConnection(1, this::onClock);

  public final int bits;

  public Register(final int bits) {
    this.bits = bits;

    this.in  = new InputConnection(bits);
    this.out = new OutputConnection(bits).setValue(0);
  }

  public void clear() {
    this.out.setValue(0);
  }

  private void onClock(final OptionalInt value) {
    if(value.getAsInt() != 0) {
      if(this.load.getValue().orElseThrow(() -> new FloatingConnectionException("Register load is floating")) != 0) {
        this.out.setValue(this.in.getValue().orElseThrow(() -> new FloatingConnectionException("Register in is floating")));
      }
    }
  }

  @Override
  public String toString() {
    return "Register [in -> " + Pins.toBits(this.in) + ", out -> " + Pins.toBits(this.out) + ']';
  }

  public String toBits() {
    return Pins.toBits(this.out);
  }

  public int toInt() {
    return this.out.getValue().getAsInt();
  }
}

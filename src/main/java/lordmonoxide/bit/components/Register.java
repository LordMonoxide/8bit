package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class Register extends Component {
  private final InputPin[] in;
  private final OutputPin[] out;

  public final InputPin load = new InputPin();
  public final InputPin clock = new InputPin(this::onClock);

  public final int size;

  public Register(final int size) {
    this.size = size;

    this.in  = new InputPin[size];
    this.out = new OutputPin[size];

    for(int pin = 0; pin < size; pin++) {
      this.in[pin] = new InputPin();
      this.out[pin] = new OutputPin();
    }
  }

  public InputPin in(final int pin) {
    return this.in[pin];
  }

  public OutputPin out(final int pin) {
    return this.out[pin];
  }

  public void clear() {
    for(int pin = 0; pin < this.size; pin++) {
      this.out[pin].setLow();
    }
  }

  private void onClock(final PinState state) {
    if(this.load.isHigh() && state == PinState.HIGH) {
      for(int pin = 0; pin < this.size; pin++) {
        if(this.in[pin].isDisconnected()) {
          throw new FloatingPinException("Register floating input pin " + pin);
        }

        this.out[pin].setState(this.in[pin].getState());
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
    return Pins.toInt(this.out);
  }
}

package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class Counter extends Component {
  private final InputPin[] in;
  private final OutputPin[] out;

  public final InputPin load = new InputPin();
  public final InputPin clock = new InputPin(this::onClock);
  public final InputPin count = new InputPin();

  public final int size;

  private int value;

  public Counter(final int size) {
    this.size = size;

    this.in = new InputPin[size];
    this.out = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      this.in[i] = new InputPin();
      this.out[i] = new OutputPin();
    }
  }

  public InputPin in(final int pin) {
    return this.in[pin];
  }

  public OutputPin out(final int pin) {
    return this.out[pin];
  }

  public void clear() {
    this.value = 0;
    this.updateOutput();
  }

  private void onClock(final PinState state) {
    if(this.count.isDisconnected()) {
      throw new FloatingPinException();
    }

    if(state == PinState.HIGH) {
      if(this.count.isHigh()) {
        this.value++;
      }

      if(this.load.isHigh()) {
        this.value = Pins.toInt(this.in);
      }

      this.updateOutput();
    }
  }

  private void updateOutput() {
    for(int i = 0; i < this.size; i++) {
      this.out(i).setState(Pins.fromInt(this.value, i));
    }
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("Counter [");

    for(int i = this.size - 1; i > 0; i--) {
      builder.append(this.out(i).getState()).append(", ");
    }

    builder.append(this.out(0).getState()).append(']');

    return builder.toString();
  }

  public String toBits() {
    return Pins.toBits(this.out);
  }

  public int toInt() {
    return Pins.toInt(this.out);
  }
}

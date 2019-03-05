package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;
import org.jetbrains.annotations.NotNull;

public class Counter extends Component {
  private final InputPin[] in;
  private final OutputPin[] out;

  public final InputPin load = new InputPin();
  public final InputPin clock = new InputPin(this::onClock);

  public final int size;

  private int count;

  @NotNull
  public static Counter eightBit() {
    return new Counter(8);
  }

  public Counter(final int size) {
    this.size = size;

    this.in = new InputPin[size];
    this.out = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      this.in[i] = new InputPin();
      this.out[i] = new OutputPin();
    }
  }

  @NotNull
  public InputPin in(final int pin) {
    return this.in[pin];
  }

  @NotNull
  public OutputPin out(final int pin) {
    return this.out[pin];
  }

  public void clear() {
    this.count = 0;
    this.updateOutput();
  }

  private void onClock(final PinState state) {
    if(state == PinState.HIGH) {
      this.count++;

      if(this.load.isHigh()) {
        this.count = Pins.toInt(this.in);
      }

      this.updateOutput();
    }
  }

  private void updateOutput() {
    for(int i = 0; i < this.size; i++) {
      this.out(i).setState(Pins.fromInt(this.count, i));
    }
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("Counter [");

    for(int i = this.size - 1; i > 0; i--) {
      builder.append(this.out(i)).append(", ");
    }

    builder.append(this.out(0)).append(']');

    return builder.toString();
  }

  public String toBits() {
    return Pins.toBits(this.out);
  }

  public int toInt() {
    return Pins.toInt(this.out);
  }
}

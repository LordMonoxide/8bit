package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.Pins;
import org.jetbrains.annotations.NotNull;

public class Register extends Component {
  private final DLatch[] bits;
  private final InputPin[] in;
  private final OutputPin[] out;

  public final InputPin load;
  public final InputPin clock;

  public final int size;

  @NotNull
  public static Register eightBit() {
    return new Register(8);
  }

  public Register(final int size) {
    this.size = size;

    this.bits = new DLatch[size];
    this.in   = new InputPin[size];
    this.out  = new OutputPin[size];

    final InputPin[] load = new InputPin[size];
    final InputPin[] clock = new InputPin[size];

    for(int i = 0; i < size; i++) {
      this.bits[i] = new DLatch();
      this.in[i] = this.bits[i].input;
      this.out[i] = this.bits[i].output;

      load[i] = this.bits[i].load;
    }

    for(int i = 0; i < size; i++) {
      clock[i] = this.bits[size - i - 1].clock;
    }

    this.load = InputPin.aggregate(load);
    this.clock = InputPin.aggregate(clock);
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
    for(int i = 0; i < this.size; i++) {
      this.bits[i].clear();
    }
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("Register [");

    for(int i = this.bits.length - 1; i > 0; i--) {
      builder.append(this.bits[i]).append(", ");
    }

    builder.append(this.bits[0]).append(']');

    return builder.toString();
  }

  public String toBits() {
    return Pins.toBits(this.out);
  }

  public int toInt() {
    return Pins.toInt(this.out);
  }
}

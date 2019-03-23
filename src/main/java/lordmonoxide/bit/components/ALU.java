package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class ALU extends Component {
  private final InputPin[] a;
  private final InputPin[] b;
  private final OutputPin[] out;

  public final InputPin carryIn;
  public final OutputPin carryOut;

  public final int size;
  private final int max;

  public ALU(final int size) {
    this.size = size;
    this.max = (int)Math.pow(2, size);

    this.a = new InputPin[size];
    this.b = new InputPin[size];
    this.out = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      this.a[i] = new InputPin(this::onInputChanged);
      this.b[i] = new InputPin(this::onInputChanged);
      this.out[i] = new OutputPin();
    }

    this.carryIn = new InputPin(this::onInputChanged);
    this.carryOut = new OutputPin();
  }

  public InputPin a(final int pin) {
    return this.a[pin];
  }

  public InputPin b(final int pin) {
    return this.b[pin];
  }

  public OutputPin out(final int pin) {
    return this.out[pin];
  }

  private void onInputChanged(final PinState state) {
    final int sum = Pins.toInt(this.a) + Pins.toInt(this.b) + Pins.toInt(this.carryIn);

    if(sum > this.max) {
      this.carryOut.setHigh();
    } else {
      this.carryOut.setLow();
    }

    final int output = sum % this.max;

    for(int pin = 0; pin < this.size; pin++) {
      this.out[pin].setState(Pins.fromInt(output, pin));
    }
  }

  @Override
  public String toString() {
    return "ALU: [" + Pins.toBits(this.a) + " + " + Pins.toBits(this.b) + " + " + Pins.toBits(this.carryIn) + " = " + Pins.toBits(this.out) + ']';
  }

  public String toBits() {
    return Pins.toBits(this.out);
  }

  public int toInt() {
    return Pins.toInt(this.out);
  }
}

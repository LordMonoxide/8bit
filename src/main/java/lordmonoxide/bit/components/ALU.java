package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.Pins;

public class ALU extends Component {
  private final FullAdder[] adders;
  private final InputPin[] a;
  private final InputPin[] b;
  private final OutputPin[] out;

  public final InputPin carryIn;
  public final OutputPin carryOut;

  public final int size;

  public ALU(final int size) {
    this.size = size;

    this.adders = new FullAdder[size];
    this.a = new InputPin[size];
    this.b = new InputPin[size];
    this.out = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      this.adders[i] = new FullAdder();
      this.a[i] = this.adders[i].a;
      this.b[i] = this.adders[i].b;
      this.out[i] = this.adders[i].out;

      if(i > 0) {
        this.adders[i].carryIn.connectTo(this.adders[i - 1].carryOut);
      }
    }

    this.carryIn = this.adders[0].carryIn;
    this.carryOut = this.adders[size - 1].carryOut;
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

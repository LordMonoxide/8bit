package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;
import lordmonoxide.bit.parts.Pins;

import java.util.OptionalInt;

public class ALU extends Component {
  public final InputConnection a;
  public final InputConnection b;
  public final OutputConnection out;

  public final InputConnection carryIn;
  public final OutputConnection carryOut;

  public final int size;
  private final int max;

  public ALU(final int size) {
    this.size = size;
    this.max = (int)Math.pow(2, size);

    this.a = new InputConnection(size, this::onInputChanged);
    this.b = new InputConnection(size, this::onInputChanged);
    this.out = new OutputConnection(size).setValue(0);

    this.carryIn = new InputConnection(1, this::onInputChanged);
    this.carryOut = new OutputConnection(1).setValue(0);
  }

  private void onInputChanged(final OptionalInt value) {
    if(this.a.getValue().isEmpty() || this.b.getValue().isEmpty() || this.carryIn.getValue().isEmpty()) {
      return;
    }

    final int sum = this.a.getValue().getAsInt() + this.b.getValue().getAsInt() + this.carryIn.getValue().getAsInt();

    if(sum > this.max) {
      this.carryOut.setValue(1);
    } else {
      this.carryOut.setValue(0);
    }

    this.out.setValue(sum % this.max);
  }

  @Override
  public String toString() {
    return "ALU: [" + Pins.toBits(this.a) + " + " + Pins.toBits(this.b) + " + " + Pins.toBits(this.carryIn) + " = " + Pins.toBits(this.out) + ']';
  }

  public String toBits() {
    return Pins.toBits(this.out);
  }

  public int toInt() {
    return this.out.getValue().getAsInt();
  }
}

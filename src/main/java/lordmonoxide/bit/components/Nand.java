package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class Nand extends Component {
  private final And and;
  private final Not not = new Not();

  private final InputPin[] in;
  public final OutputPin out = this.not.out;

  public Nand(final int size) {
    this.and = new And(size);
    this.not.in.connectTo(this.and.out);

    this.in = new InputPin[size];

    for(int i = 0; i < size; i++) {
      this.in[i] = this.and.in(i);
    }
  }

  public Nand() {
    this(2);
  }

  public InputPin in(final int pin) {
    return this.in[pin];
  }
}

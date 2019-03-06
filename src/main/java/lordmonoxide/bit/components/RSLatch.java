package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class RSLatch extends Component {
  private final Nand sNand = new Nand();
  private final Nand rNand = new Nand();

  public final InputPin s = this.sNand.in(0);
  public final InputPin r = this.rNand.in(1);

  public final OutputPin q = this.sNand.out;
  public final OutputPin q2 = this.rNand.out;

  public RSLatch() {
    this.sNand.in(1).connectTo(this.rNand.out);
    this.rNand.in(0).connectTo(this.sNand.out);
  }
}

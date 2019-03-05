package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class FullAdder extends Component {
  private final Xor aXorB = new Xor();
  private final And aAndB = new And();
  private final And aXorBAndC = new And();
  private final Xor sumXor = new Xor();
  private final Or  carryOr = new Or();

  public final InputPin a = InputPin.aggregate(this.aXorB.in(0), this.aAndB.in(0));
  public final InputPin b = InputPin.aggregate(this.aXorB.in(1), this.aAndB.in(1));
  public final InputPin carryIn = InputPin.aggregate(this.sumXor.in(1), this.aXorBAndC.in(1));
  public final OutputPin out = this.sumXor.out;
  public final OutputPin carryOut = this.carryOr.out;

  public FullAdder() {
    this.sumXor.in(0).connectTo(this.aXorB.out);
    this.aXorBAndC.in(0).connectTo(this.aXorB.out);
    this.carryOr.in(0).connectTo(this.aXorBAndC.out);
    this.carryOr.in(1).connectTo(this.aAndB.out);
  }

  @Override
  public String toString() {
    return "Full Adder [" + this.a.getState() + " + " + this.b.getState() + " (+ " + this.carryIn.getState() + ") = " + this.out.getState() + " (carry " + this.carryOut.getState() + ")]";
  }
}

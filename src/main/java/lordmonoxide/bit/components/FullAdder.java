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

  public final InputPin a = InputPin.aggregate(this.aXorB.a, this.aAndB.a);
  public final InputPin b = InputPin.aggregate(this.aXorB.b, this.aAndB.b);
  public final InputPin carryIn = InputPin.aggregate(this.sumXor.b, this.aXorBAndC.b);
  public final OutputPin out = this.sumXor.out;
  public final OutputPin carryOut = this.carryOr.out;

  public FullAdder() {
    this.sumXor.a.connectTo(this.aXorB.out);
    this.aXorBAndC.a.connectTo(this.aXorB.out);
    this.carryOr.a.connectTo(this.aXorBAndC.out);
    this.carryOr.b.connectTo(this.aAndB.out);
  }

  @Override
  public String toString() {
    return "Full Adder [" + this.a.getState() + " + " + this.b.getState() + " (+ " + this.carryIn.getState() + ") = " + this.out.getState() + " (carry " + this.carryOut.getState() + ")]";
  }
}

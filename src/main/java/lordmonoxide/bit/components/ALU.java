package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class ALU extends Component {
  private final FullAdder[] adders = {
    new FullAdder(), new FullAdder(), new FullAdder(), new FullAdder(),
    new FullAdder(), new FullAdder(), new FullAdder(), new FullAdder(),
  };

  public final InputPin a0 = this.adders[0].a;
  public final InputPin a1 = this.adders[1].a;
  public final InputPin a2 = this.adders[2].a;
  public final InputPin a3 = this.adders[3].a;
  public final InputPin a4 = this.adders[4].a;
  public final InputPin a5 = this.adders[5].a;
  public final InputPin a6 = this.adders[6].a;
  public final InputPin a7 = this.adders[7].a;

  public final InputPin b0 = this.adders[0].b;
  public final InputPin b1 = this.adders[1].b;
  public final InputPin b2 = this.adders[2].b;
  public final InputPin b3 = this.adders[3].b;
  public final InputPin b4 = this.adders[4].b;
  public final InputPin b5 = this.adders[5].b;
  public final InputPin b6 = this.adders[6].b;
  public final InputPin b7 = this.adders[7].b;

  public final OutputPin out0 = this.adders[0].out;
  public final OutputPin out1 = this.adders[1].out;
  public final OutputPin out2 = this.adders[2].out;
  public final OutputPin out3 = this.adders[3].out;
  public final OutputPin out4 = this.adders[4].out;
  public final OutputPin out5 = this.adders[5].out;
  public final OutputPin out6 = this.adders[6].out;
  public final OutputPin out7 = this.adders[7].out;

  public final InputPin carryIn = this.adders[0].carryIn;
  public final OutputPin carryOut = this.adders[7].carryOut;

  public ALU() {
    for(int i = 1; i < this.adders.length; i++) {
      this.adders[i].carryIn.connectTo(this.adders[i - 1].carryOut);
    }
  }

  @Override
  public String toString() {
    return "ALU: carry in: " + this.carryIn.getState() + '\n' +
      this.a0.getState() + " + " + this.b0.getState() + " = " + this.out0.getState() + '\n' +
      this.a1.getState() + " + " + this.b1.getState() + " = " + this.out1.getState() + '\n' +
      this.a2.getState() + " + " + this.b2.getState() + " = " + this.out2.getState() + '\n' +
      this.a3.getState() + " + " + this.b3.getState() + " = " + this.out3.getState() + '\n' +
      this.a4.getState() + " + " + this.b4.getState() + " = " + this.out4.getState() + '\n' +
      this.a5.getState() + " + " + this.b5.getState() + " = " + this.out5.getState() + '\n' +
      this.a6.getState() + " + " + this.b6.getState() + " = " + this.out6.getState() + '\n' +
      this.a7.getState() + " + " + this.b7.getState() + " = " + this.out7.getState() + '\n' +
      "Carry out: " + this.carryOut.getState();
  }
}

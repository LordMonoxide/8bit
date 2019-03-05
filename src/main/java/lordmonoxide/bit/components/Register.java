package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class Register extends Component {
  private final DLatch[] bits = {
    new DLatch(), new DLatch(), new DLatch(), new DLatch(),
    new DLatch(), new DLatch(), new DLatch(), new DLatch(),
  };

  public final InputPin in0 = this.bits[0].input;
  public final InputPin in1 = this.bits[1].input;
  public final InputPin in2 = this.bits[2].input;
  public final InputPin in3 = this.bits[3].input;
  public final InputPin in4 = this.bits[4].input;
  public final InputPin in5 = this.bits[5].input;
  public final InputPin in6 = this.bits[6].input;
  public final InputPin in7 = this.bits[7].input;

  public final OutputPin out0 = this.bits[0].output;
  public final OutputPin out1 = this.bits[1].output;
  public final OutputPin out2 = this.bits[2].output;
  public final OutputPin out3 = this.bits[3].output;
  public final OutputPin out4 = this.bits[4].output;
  public final OutputPin out5 = this.bits[5].output;
  public final OutputPin out6 = this.bits[6].output;
  public final OutputPin out7 = this.bits[7].output;

  public final InputPin load = InputPin.aggregate(this.bits[0].load, this.bits[1].load, this.bits[2].load, this.bits[3].load, this.bits[4].load, this.bits[5].load, this.bits[6].load, this.bits[7].load);
  public final InputPin clock = InputPin.aggregate(this.bits[7].clock, this.bits[6].clock, this.bits[5].clock, this.bits[4].clock, this.bits[3].clock, this.bits[2].clock, this.bits[1].clock, this.bits[0].clock);

  @Override
  public String toString() {
    return "Register [" +
      this.bits[7] + ", " + this.bits[6] + ", " + this.bits[5] + ", " + this.bits[4] + ", " +
      this.bits[3] + ", " + this.bits[2] + ", " + this.bits[1] + ", " + this.bits[0] + ']';
  }

  public String toBits() {
    return
      String.valueOf(this.bits[7].output.getState().toInt()) +
      this.bits[6].output.getState().toInt() +
      this.bits[5].output.getState().toInt() +
      this.bits[4].output.getState().toInt() +
      this.bits[3].output.getState().toInt() +
      this.bits[2].output.getState().toInt() +
      this.bits[1].output.getState().toInt() +
      this.bits[0].output.getState().toInt();
  }

  public int toInt() {
    return
      this.bits[0].output.getState().toInt() |
      this.bits[1].output.getState().toInt() << 1 |
      this.bits[2].output.getState().toInt() << 2 |
      this.bits[3].output.getState().toInt() << 3 |
      this.bits[4].output.getState().toInt() << 4 |
      this.bits[5].output.getState().toInt() << 5 |
      this.bits[6].output.getState().toInt() << 6 |
      this.bits[7].output.getState().toInt() << 7;
  }
}

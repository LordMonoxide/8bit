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

  public final InputPin clock = new InputPin(state -> {
    for(final DLatch latch : this.bits) {
      latch.clock.setState(state);
    }
  });

  @Override
  public String toString() {
    return "Register [" +
      this.bits[0] + ", " + this.bits[1] + ", " + this.bits[2] + ", " + this.bits[3] + ", " +
      this.bits[4] + ", " + this.bits[5] + ", " + this.bits[6] + ", " + this.bits[7] + ']';
  }
}

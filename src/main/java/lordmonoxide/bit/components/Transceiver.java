package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import org.jetbrains.annotations.NotNull;

public class Transceiver extends Component {
  private final InputPin[] aIn;
  private final InputPin[] bIn;
  private final OutputPin[] aOut;
  private final OutputPin[] bOut;

  /**
   * LOW = b->a, HIGH = a->b
   */
  public final InputPin dir = new InputPin(this::changeOutput);

  public final InputPin enable = new InputPin(this::changeOutput);

  public final int size;

  @NotNull
  public static Transceiver eightBit() {
    return new Transceiver(8);
  }

  public Transceiver(final int size) {
    this.size = size;

    this.aIn = new InputPin[size];
    this.bIn = new InputPin[size];
    this.aOut = new OutputPin[size];
    this.bOut = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      final int i1 = i;
      this.aIn[i] = new InputPin(state -> this.changeState(this.aIn[i1], this.aOut[i1]));
      this.bIn[i] = new InputPin(state -> this.changeState(this.bIn[i1], this.bOut[i1]));
      this.aOut[i] = new OutputPin();
      this.bOut[i] = new OutputPin();
    }
  }

  @NotNull
  public InputPin aIn(final int pin) {
    return this.aIn[pin];
  }

  @NotNull
  public InputPin bIn(final int pin) {
    return this.bIn[pin];
  }

  @NotNull
  public OutputPin aOut(final int pin) {
    return this.aOut[pin];
  }

  @NotNull
  public OutputPin bOut(final int pin) {
    return this.bOut[pin];
  }

  private void changeOutput(final PinState state) {
    if(this.dir.isDisconnected() || this.enable.isDisconnected()) {
      throw new FloatingPinException();
    }

    for(int i = 0; i < this.size; i++) {
      this.aOut[0].disable();
      this.bOut[0].disable();
    }

    if(this.enable.isHigh()) {
      if(this.dir.isHigh()) {
        for(int i = 0; i < this.size; i++) {
          this.aOut[0].enable();
        }
      }

      if(this.dir.isLow()) {
        for(int i = 0; i < this.size; i++) {
          this.bOut[0].enable();
        }
      }
    }
  }

  private void changeState(final InputPin in, final OutputPin out) {
    if(this.dir.isDisconnected()) {
      throw new FloatingPinException();
    }

    out.setState(in.getState());
  }
}

package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;

public class Transceiver extends Component {
  public final InputPin aIn0 = new InputPin(state -> this.changeState(this.aIn0, this.aOut0));
  public final InputPin aIn1 = new InputPin(state -> this.changeState(this.aIn1, this.aOut1));
  public final InputPin aIn2 = new InputPin(state -> this.changeState(this.aIn2, this.aOut2));
  public final InputPin aIn3 = new InputPin(state -> this.changeState(this.aIn3, this.aOut3));
  public final InputPin aIn4 = new InputPin(state -> this.changeState(this.aIn4, this.aOut4));
  public final InputPin aIn5 = new InputPin(state -> this.changeState(this.aIn5, this.aOut5));
  public final InputPin aIn6 = new InputPin(state -> this.changeState(this.aIn6, this.aOut6));
  public final InputPin aIn7 = new InputPin(state -> this.changeState(this.aIn7, this.aOut7));

  public final InputPin bIn0 = new InputPin(state -> this.changeState(this.bIn0, this.bOut0));
  public final InputPin bIn1 = new InputPin(state -> this.changeState(this.bIn1, this.bOut1));
  public final InputPin bIn2 = new InputPin(state -> this.changeState(this.bIn2, this.bOut2));
  public final InputPin bIn3 = new InputPin(state -> this.changeState(this.bIn3, this.bOut3));
  public final InputPin bIn4 = new InputPin(state -> this.changeState(this.bIn4, this.bOut4));
  public final InputPin bIn5 = new InputPin(state -> this.changeState(this.bIn5, this.bOut5));
  public final InputPin bIn6 = new InputPin(state -> this.changeState(this.bIn6, this.bOut6));
  public final InputPin bIn7 = new InputPin(state -> this.changeState(this.bIn7, this.bOut7));

  public final OutputPin aOut0 = new OutputPin();
  public final OutputPin aOut1 = new OutputPin();
  public final OutputPin aOut2 = new OutputPin();
  public final OutputPin aOut3 = new OutputPin();
  public final OutputPin aOut4 = new OutputPin();
  public final OutputPin aOut5 = new OutputPin();
  public final OutputPin aOut6 = new OutputPin();
  public final OutputPin aOut7 = new OutputPin();

  public final OutputPin bOut0 = new OutputPin();
  public final OutputPin bOut1 = new OutputPin();
  public final OutputPin bOut2 = new OutputPin();
  public final OutputPin bOut3 = new OutputPin();
  public final OutputPin bOut4 = new OutputPin();
  public final OutputPin bOut5 = new OutputPin();
  public final OutputPin bOut6 = new OutputPin();
  public final OutputPin bOut7 = new OutputPin();

  /**
   * LOW = b->a, HIGH = a->b
   */
  public final InputPin dir = new InputPin(this::changeOutput);

  public final InputPin enable = new InputPin(this::changeOutput);

  private void changeOutput(final PinState state) {
    if(this.dir.isDisconnected() || this.enable.isDisconnected()) {
      throw new FloatingPinException();
    }

    this.aOut0.disable();
    this.aOut1.disable();
    this.aOut2.disable();
    this.aOut3.disable();
    this.aOut4.disable();
    this.aOut5.disable();
    this.aOut6.disable();
    this.aOut7.disable();
    this.bOut0.disable();
    this.bOut1.disable();
    this.bOut2.disable();
    this.bOut3.disable();
    this.bOut4.disable();
    this.bOut5.disable();
    this.bOut6.disable();
    this.bOut7.disable();

    if(this.enable.isHigh()) {
      if(this.dir.isHigh()) {
        this.aOut0.enable();
        this.aOut1.enable();
        this.aOut2.enable();
        this.aOut3.enable();
        this.aOut4.enable();
        this.aOut5.enable();
        this.aOut6.enable();
        this.aOut7.enable();
      }

      if(this.dir.isLow()) {
        this.bOut0.enable();
        this.bOut1.enable();
        this.bOut2.enable();
        this.bOut3.enable();
        this.bOut4.enable();
        this.bOut5.enable();
        this.bOut6.enable();
        this.bOut7.enable();
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

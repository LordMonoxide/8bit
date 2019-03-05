package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;

public class DLatch extends Component {
  public final InputPin input = new InputPin();
  public final OutputPin output = new OutputPin();

  public final InputPin load = new InputPin();

  public final InputPin clock = new InputPin(state -> {
    if(this.load.isHigh() && state == PinState.HIGH) {
      if(this.input.isDisconnected()) {
        throw new FloatingPinException();
      }

      this.output.setState(this.input.getState());
    }
  });

  public void clear() {
    this.output.setLow();
  }

  @Override
  public String toString() {
    return "D Latch [" + this.input.getState() + " -> " + this.output.getState() + ']';
  }
}

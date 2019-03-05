package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;

public class DLatch extends Component {
  public final InputPin input = new InputPin();
  public final OutputPin output = new OutputPin();

  public final InputPin clock = new InputPin(state -> {
    if(state == PinState.HIGH) {
      this.output.setState(this.input.getState());
    }
  });

  @Override
  public String toString() {
    return "D Latch [" + this.input.getState() + " -> " + this.output.getState() + ']';
  }
}

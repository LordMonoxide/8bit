package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class Not extends Component {
  public final InputPin in = new InputPin(state -> this.out.setState(state.not()));
  public final OutputPin out = new OutputPin().setLow();

  @Override
  public String toString() {
    return "Not [" + this.in.getState() + " ~ " + this.out.getState() + ']';
  }
}

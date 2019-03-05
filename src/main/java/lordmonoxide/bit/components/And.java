package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class And extends Component {
  public final InputPin a = new InputPin(state -> this.out.setState(state.and(this.b.getState())));
  public final InputPin b = new InputPin(state -> this.out.setState(state.and(this.a.getState())));
  public final OutputPin out = new OutputPin();

  @Override
  public String toString() {
    return "And [" + this.a.getState() + " & " + this.b.getState() + " = " + this.out.getState() + ']';
  }
}

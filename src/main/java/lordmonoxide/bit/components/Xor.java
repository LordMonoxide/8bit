package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class Xor extends Component {
  public final InputPin a = new InputPin(state -> this.out.setState(state.xor(this.b.getState())));
  public final InputPin b = new InputPin(state -> this.out.setState(state.xor(this.a.getState())));
  public final OutputPin out = new OutputPin().setLow();

  @Override
  public String toString() {
    return "Xor [" + this.a.getState() + " ^ " + this.b.getState() + " = " + this.out.getState() + ']';
  }
}

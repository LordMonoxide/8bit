package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;

public class Xor extends Component {
  private final InputPin[] in;
  public final OutputPin out = new OutputPin();

  public final int size;

  public Xor(final int size) {
    this.size = size;

    this.in = new InputPin[size];

    for(int i = 0; i < size; i++) {
      this.in[i] = new InputPin(this::updateOutput);
    }
  }

  public Xor() {
    this(2);
  }

  public InputPin in(final int pin) {
    return this.in[pin];
  }

  private void updateOutput(final PinState newState) {
    PinState state = this.in[0].getState();

    for(int i = 1; i < this.size; i++) {
      state = state.xor(this.in[i].getState());
    }

    this.out.setState(state);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder("Xor [").append(this.in[0].getState());

    for(int i = 1; i < this.size; i++) {
      builder.append(" ^ ").append(this.in[i].getState());
    }

    builder.append(" = ").append(this.out.getState()).append(']');

    return builder.toString();
  }
}

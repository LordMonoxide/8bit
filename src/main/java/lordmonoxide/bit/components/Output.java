package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class Output extends Component {
  private final Register register;

  private final InputPin[] in;

  public final InputPin load;
  public final InputPin clock;

  public final int size;

  public Output(final int size) {
    this.size = size;
    this.register = new Register(size);
    this.in = new InputPin[size];
    this.load = this.register.load;
    this.clock = InputPin.aggregate(this.register.clock, new InputPin(this::onClock));

    for(int pin = 0; pin < size; pin++) {
      this.in[pin] = this.register.in(pin);
    }
  }

  public InputPin in(final int pin) {
    return this.register.in(pin);
  }

  public void clear() {
    this.register.clear();
  }

  private void onClock(final PinState state) {
    if(state == PinState.HIGH) {
      System.out.println(Pins.toInt(this.in));
    }
  }
}

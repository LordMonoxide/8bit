package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class RegisterBoard extends Board {
  public final String name;
  private final Register register;

  public final InputPin enable;
  public final InputPin clock;
  public final InputPin input;

  public RegisterBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.register = new Register(size);
    this.enable = this.getTransceiver().enable;
    this.clock = this.register.clock;
    this.input = InputPin.aggregate(this.register.load, this.getTransceiver().dir);

    for(int i = 0; i < this.size; i++) {
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.register.out(i));
      this.register.in(i).connectTo(this.getTransceiver().out(TransceiverSide.B, i));
    }
  }

  public OutputPin out(final int pin) {
    return this.register.out(pin);
  }

  public void clear() {
    this.register.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.register;
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.And;
import lordmonoxide.bit.components.Not;
import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class DisablableRegisterBoard extends Board {
  public final String name;
  private final Register register;
  private final And[] and;
  private final Not not = new Not();

  public final InputPin enable;
  public final InputPin clock;
  public final InputPin input;
  public final InputPin disable;

  public DisablableRegisterBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.register = new Register(size);
    this.enable = this.getTransceiver().enable;
    this.clock = this.register.clock;
    this.input = InputPin.aggregate(this.register.load, this.getTransceiver().dir);
    this.disable = this.not.in;

    this.and = new And[size];

    for(int i = 0; i < size; i++) {
      this.and[i] = new And();
      this.and[i].in(0).connectTo(this.not.out);
      this.and[i].in(1).connectTo(this.register.out(i));
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.register.out(i));
      this.register.in(i).connectTo(this.getTransceiver().out(TransceiverSide.B, i));
    }
  }

  public OutputPin out(final int pin) {
    return this.and[pin].out;
  }

  public void clear() {
    this.register.clear();
  }

  @Override
  public String toString() {
    return this.register.toString();
  }
}
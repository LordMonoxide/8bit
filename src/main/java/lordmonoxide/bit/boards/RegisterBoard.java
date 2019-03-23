package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;

public class RegisterBoard extends Board {
  public final String name;
  private final Register register;

  public final InputConnection enable;
  public final InputConnection clock;
  public final InputConnection input;
  public final OutputConnection out;

  public RegisterBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.register = new Register(size);
    this.enable = this.getTransceiver().enable;
    this.clock = this.register.clock;
    this.input = InputConnection.aggregate(1, this.register.load, this.getTransceiver().dir);
    this.out = this.register.out;

    this.getTransceiver().in(TransceiverSide.B).connectTo(this.register.out);
    this.register.in.connectTo(this.getTransceiver().out(TransceiverSide.B));
  }

  public void clear() {
    this.register.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.register;
  }
}

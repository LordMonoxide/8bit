package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.And;
import lordmonoxide.bit.components.Not;
import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;

public class DisablableRegisterBoard extends Board {
  public final String name;
  private final Register register;
  private final And and;
  private final Not not;

  public final InputConnection enable;
  public final InputConnection clock;
  public final InputConnection input;
  public final InputConnection disable;
  public final OutputConnection out;

  public DisablableRegisterBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.not = new Not(size);

    this.register = new Register(size);
    this.enable = this.getTransceiver().enable;
    this.clock = this.register.clock;
    this.input = InputConnection.aggregate(1, this.register.load, this.getTransceiver().dir);
    this.disable = this.not.in;

    this.and = new And(size);
    this.and.in(0).connectTo(this.not.out);
    this.and.in(1).connectTo(this.register.out);
    this.out = this.and.out;

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

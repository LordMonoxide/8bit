package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import org.jetbrains.annotations.NotNull;

public class RegisterBoard extends Board {
  private final Register register;

  public final InputPin load;
  public final InputPin clock;

  public RegisterBoard(final int size) {
    super(size);

    this.register = new Register(size);
    this.load = this.register.load;
    this.clock = this.register.clock;

    for(int i = 0; i < this.size; i++) {
      this.transceiver.in(TransceiverSide.B, i).connectTo(this.register.out(i));
      this.register.in(i).connectTo(this.transceiver.out(TransceiverSide.B, i));
    }
  }

  @NotNull
  public OutputPin out(final int pin) {
    return this.register.out(pin);
  }

  public void clear() {
    this.register.clear();
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;

public class RegisterBoard extends Board {
  public final Register register = Register.eightBit();

  public RegisterBoard() {
    for(int i = 0; i < this.transceiver.size; i++) {
      this.transceiver.in(TransceiverSide.B, i).connectTo(this.register.out(i));
      this.register.in(i).connectTo(this.transceiver.out(TransceiverSide.B, i));
    }
  }
}

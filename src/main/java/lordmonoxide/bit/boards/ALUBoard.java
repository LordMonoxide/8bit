package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import org.jetbrains.annotations.NotNull;

public class ALUBoard extends Board {
  private final ALU alu;

  public ALUBoard(final int size) {
    super(size);

    this.alu = new ALU(size);
    this.alu.carryIn.setLow();

    this.transceiver.dir.setLow();

    for(int i = 0; i < this.size; i++ ) {
      this.transceiver.in(TransceiverSide.B, i).connectTo(this.alu.out(i));
    }
  }

  @NotNull
  public InputPin a(final int pin) {
    return this.alu.a(pin);
  }

  @NotNull
  public InputPin b(final int pin) {
    return this.alu.b(pin);
  }
}

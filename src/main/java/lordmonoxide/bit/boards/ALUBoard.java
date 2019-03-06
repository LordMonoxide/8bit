package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.TransceiverSide;

public class ALUBoard extends Board {
  public final ALU alu = ALU.eightBit();

  public ALUBoard() {
    this.alu.carryIn.setLow();

    this.transceiver.dir.setLow();

    for(int i = 0; i < this.transceiver.size; i++ ) {
      this.transceiver.in(TransceiverSide.B, i).connectTo(this.alu.out(i));
    }
  }
}

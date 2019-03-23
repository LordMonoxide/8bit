package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.Pins;

public class ALUBoard extends Board {
  public final String name;
  private final ALU alu;

  public final InputConnection a;
  public final InputConnection b;
  public final InputConnection enable;

  public ALUBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.alu = new ALU(size);
    this.alu.carryIn.connectTo(Pins.GND);
    this.a = this.alu.a;
    this.b = this.alu.b;
    this.enable = this.getTransceiver().enable;

    this.getTransceiver().dir.connectTo(Pins.GND);
    this.getTransceiver().in(TransceiverSide.B).connectTo(this.alu.out);
  }

  @Override
  public String toString() {
    return this.name + ": " + this.alu;
  }
}

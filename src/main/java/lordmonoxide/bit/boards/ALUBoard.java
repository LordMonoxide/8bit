package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.Pins;

public class ALUBoard extends Board {
  public final String name;
  private final ALU alu;

  public final InputPin enable;

  public ALUBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.alu = new ALU(size);
    this.alu.carryIn.connectTo(Pins.GND);
    this.enable = this.getTransceiver().enable;

    this.getTransceiver().dir.connectTo(Pins.GND);

    for(int i = 0; i < this.size; i++ ) {
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.alu.out(i));
    }
  }

  public InputPin a(final int pin) {
    return this.alu.a(pin);
  }

  public InputPin b(final int pin) {
    return this.alu.b(pin);
  }

  @Override
  public String toString() {
    return this.name + ": " + this.alu;
  }
}

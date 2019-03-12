package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;

public class ALUBoard extends Board {
  public final String name;
  private final ALU alu;

  public final InputPin enable;

  public ALUBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.alu = new ALU(size);
    this.alu.carryIn.setLow();
    this.enable = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " EN " + state)), this.getTransceiver().enable);

    this.getTransceiver().dir.setLow();

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
}

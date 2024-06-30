package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Alu;
import lordmonoxide.bit.components.Nor;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;
import lordmonoxide.bit.parts.Pins;

public class AluBoard extends Board {
  public final String name;
  private final Alu alu;

  public final InputConnection a;
  public final InputConnection b;
  public final InputConnection enable;
  public final InputConnection sub;

  public final OutputConnection zero;
  public final OutputConnection carry;

  private final Nor zeroNor;

  public AluBoard(final String name, final int bits) {
    super(bits);

    this.name = name;

    this.alu = new Alu(bits);
    this.zeroNor = new Nor(1, bits);

    this.sub = this.alu.sub;

    this.a = this.alu.a;
    this.b = this.alu.b;
    this.enable = this.getTransceiver().enable;
    this.carry = this.alu.carryOut;

    this.alu.carryIn.connectTo(Pins.GND);

    int bit = 0;
    for(final OutputConnection outBit : OutputConnection.split(this.alu.out)) {
      this.zeroNor.in(bit).connectTo(outBit);
      bit++;
    }

    this.zero = this.zeroNor.out;

    this.getTransceiver().dir.connectTo(Pins.GND);
    this.getTransceiver().in(TransceiverSide.B).connectTo(this.alu.out);
  }

  @Override
  public String toString() {
    return this.name + ": " + this.alu;
  }
}

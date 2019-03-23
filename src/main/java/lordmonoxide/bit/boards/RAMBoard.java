package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.RAM;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;

public class RAMBoard extends Board {
  public final String name;
  private final RAM ram;

  public final InputConnection enable;
  public final InputConnection clock;
  public final InputConnection input;
  public final InputConnection bank;
  public final InputConnection address;

  public RAMBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.ram = new RAM(size);
    this.enable = this.getTransceiver().enable;
    this.clock = this.ram.clock;
    this.input = InputConnection.aggregate(1, this.ram.load, this.getTransceiver().dir);
    this.bank = this.ram.bank;
    this.address = this.ram.address;

    this.getTransceiver().in(TransceiverSide.B).connectTo(this.ram.out);
    this.ram.in.connectTo(this.getTransceiver().out(TransceiverSide.B));
  }

  public void set(final int address, final int value) {
    this.ram.set(address, value);
  }

  public void clear() {
    this.ram.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.ram;
  }
}

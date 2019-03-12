package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.RAM;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;

public class RAMBoard extends Board {
  public final String name;
  private final RAM ram;

  public final InputPin enable;
  public final InputPin clock;
  public final InputPin input;

  public RAMBoard(final String name, final int addressSize, final int wordSize) {
    super(wordSize);

    this.name = name;

    this.ram = new RAM(addressSize, wordSize);
    this.enable = this.getTransceiver().enable;
    this.clock = this.ram.clock;
    this.input = InputPin.aggregate(this.ram.load, this.getTransceiver().dir);

    for(int i = 0; i < this.size; i++) {
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.ram.out(i));
      this.ram.in(i).connectTo(this.getTransceiver().out(TransceiverSide.B, i));
    }
  }

  public InputPin address(final int pin) {
    return this.ram.address(pin);
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

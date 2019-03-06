package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.RAM;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import org.jetbrains.annotations.NotNull;

public class RAMBoard extends Board {
  private final RAM ram;

  public final InputPin load;
  public final InputPin clock;

  public RAMBoard(final int addressSize, final int wordSize) {
    super(wordSize);

    this.ram = new RAM(addressSize, wordSize);
    this.load = this.ram.load;
    this.clock = this.ram.clock;

    for(int i = 0; i < this.size; i++) {
      this.transceiver.in(TransceiverSide.B, i).connectTo(this.ram.out(i));
      this.ram.in(i).connectTo(this.transceiver.out(TransceiverSide.B, i));
    }
  }

  @NotNull
  public InputPin address(final int pin) {
    return this.ram.address(pin);
  }

  public void clear() {
    this.ram.clear();
  }

  @Override
  public String toString() {
    return this.ram.toString();
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Transceiver;
import lordmonoxide.bit.parts.Pins;

public abstract class Board {
  private final Transceiver transceiver;

  public final int bits;

  protected Board(final int bits) {
    this.bits = bits;
    this.transceiver = new Transceiver(bits);
    this.transceiver.dir.connectTo(Pins.GND);
    this.transceiver.enable.connectTo(Pins.GND);
  }

  Transceiver getTransceiver() {
    return this.transceiver;
  }
}

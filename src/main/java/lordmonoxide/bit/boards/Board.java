package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Transceiver;
import lordmonoxide.bit.parts.Pins;

public abstract class Board {
  private final Transceiver transceiver;

  public final int size;

  protected Board(final int size) {
    this.size = size;
    this.transceiver = new Transceiver(size);
    this.transceiver.dir.connectTo(Pins.GND);
    this.transceiver.enable.connectTo(Pins.GND);
  }

  Transceiver getTransceiver() {
    return this.transceiver;
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Transceiver;

public abstract class Board {
  public final Transceiver transceiver = Transceiver.eightBit();

  protected Board() {
    this.transceiver.dir.setLow();
    this.transceiver.enable.setLow();
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Transceiver;

public abstract class Board {
  private final Transceiver transceiver;

  public final int size;

  protected Board(final int size) {
    this.size = size;
    this.transceiver = new Transceiver(size);
    this.transceiver.dir.setLow();
    this.transceiver.enable.setLow();
  }

  Transceiver getTransceiver() {
    return this.transceiver;
  }
}

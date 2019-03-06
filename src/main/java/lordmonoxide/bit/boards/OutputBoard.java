package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Output;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;

public class OutputBoard extends Board {
  private final Output output;

  public final InputPin load;
  public final InputPin clock;

  public OutputBoard(final int size) {
    super(size);

    this.output = new Output(size);
    this.load = this.output.load;
    this.clock = this.output.clock;

    this.transceiver.dir.setHigh();

    for(int pin = 0; pin < this.size; pin++ ) {
      this.output.in(pin).connectTo(this.transceiver.out(TransceiverSide.B, pin));
    }
  }

  public void clear() {
    this.output.clear();
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Output;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;

public class OutputBoard extends Board {
  private final Output output = Output.eightBit();

  public final InputPin load = this.output.load;
  public final InputPin clock = this.output.clock;

  public OutputBoard() {
    this.transceiver.dir.setHigh();

    for(int pin = 0; pin < this.transceiver.size; pin++ ) {
      this.output.in(pin).connectTo(this.transceiver.out(TransceiverSide.B, pin));
    }
  }
}

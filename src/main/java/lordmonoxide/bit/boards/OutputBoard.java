package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Output;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.Pins;

public class OutputBoard extends Board {
  public final String name;
  private final Output output;

  public final InputConnection enable;
  public final InputConnection input;
  public final InputConnection clock;

  public OutputBoard(final String name, final int bits) {
    super(bits);

    this.name = name;

    this.output = new Output(bits);
    this.enable = this.getTransceiver().enable;
    this.input = this.output.load;
    this.clock = this.output.clock;

    this.getTransceiver().dir.connectTo(Pins.VCC);

    this.output.in.connectTo(this.getTransceiver().out(TransceiverSide.B));
  }

  public void clear() {
    this.output.clear();
  }
}

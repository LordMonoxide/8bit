package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Output;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.Pins;

public class OutputBoard extends Board {
  public final String name;
  private final Output output;

  public final InputPin enable;
  public final InputPin input;
  public final InputPin clock;

  public OutputBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.output = new Output(size);
    this.enable = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " EN " + state)), this.getTransceiver().enable);
    this.input = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " IN " + state)), this.output.load);
    this.clock = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " CLK " + state)), this.output.clock);

    this.getTransceiver().dir.connectTo(Pins.VCC);

    for(int pin = 0; pin < this.size; pin++ ) {
      this.output.in(pin).connectTo(this.getTransceiver().out(TransceiverSide.B, pin));
    }
  }

  public void clear() {
    this.output.clear();
  }
}

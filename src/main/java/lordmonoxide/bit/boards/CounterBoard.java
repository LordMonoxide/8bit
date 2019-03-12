package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Counter;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;

public class CounterBoard extends Board {
  public final String name;
  private final Counter counter;

  public final InputPin enable;
  public final InputPin clock;
  public final InputPin input;
  public final InputPin count;

  public CounterBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.counter = new Counter(size);
    this.enable = this.getTransceiver().enable;
    this.clock = this.counter.clock;
    this.count = this.counter.count;
    this.input = InputPin.aggregate(this.counter.load, this.getTransceiver().dir);

    for(int i = 0; i < this.size; i++) {
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.counter.out(i));
      this.counter.in(i).connectTo(this.getTransceiver().out(TransceiverSide.B, i));
    }
  }

  public void clear() {
    this.counter.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.counter;
  }
}

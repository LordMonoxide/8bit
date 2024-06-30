package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Counter;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputConnection;

public class CounterBoard extends Board {
  public final String name;
  private final Counter counter;

  public final InputConnection enable;
  public final InputConnection clock;
  public final InputConnection input;
  public final InputConnection count;

  public CounterBoard(final String name, final int bits) {
    super(bits);

    this.name = name;

    this.counter = new Counter(bits);
    this.enable = this.getTransceiver().enable;
    this.clock = this.counter.clock;
    this.count = this.counter.count;
    this.input = InputConnection.aggregate(1, this.counter.load, this.getTransceiver().dir);

    this.getTransceiver().in(TransceiverSide.B).connectTo(this.counter.out);
    this.counter.in.connectTo(this.getTransceiver().out(TransceiverSide.B));
  }

  public void clear() {
    this.counter.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.counter;
  }
}

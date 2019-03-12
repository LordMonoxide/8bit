package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Transceiver;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

import java.util.HashMap;
import java.util.Map;

public class Bus extends Component {
  private final Map<Transceiver, InputPin[]> in = new HashMap<>();
  private final OutputPin[] out;

  public final int size;

  public static Bus eightBit() {
    return new Bus(8);
  }

  public Bus(final int size) {
    this.size = size;
    this.out = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      this.out[i] = new OutputPin();
    }
  }

  public OutputPin out(final int pin) {
    return this.out[pin];
  }

  public void connect(final Board board) {
    final InputPin[] in = new InputPin[this.size];

    for(int pin = 0; pin < this.size; pin++) {
      final int pin1 = pin;
      in[pin] = new InputPin(state -> this.updateOutput(pin1));
      in[pin].connectTo(board.getTransceiver().out(TransceiverSide.A, pin));
      board.getTransceiver().in(TransceiverSide.A, pin).connectTo(this.out(pin));
    }

    this.in.put(board.getTransceiver(), in);
  }

  private void updateOutput(final int pin) {
    PinState state = PinState.LOW;

    for(final InputPin[] entry : this.in.values()) {
      if(entry[pin].isHigh()) {
        state = PinState.HIGH;
        break;
      }
    }

    if(state != this.out[pin].getState()) {
      this.out[pin].setState(state);
    }
  }

  @Override
  public String toString() {
    return "Bus: " + Pins.toBits(this.out);
  }
}

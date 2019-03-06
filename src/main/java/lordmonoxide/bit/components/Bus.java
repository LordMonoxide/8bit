package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Bus extends Component {
  private final Map<Transceiver, InputPin[]> in = new HashMap<>();
  private final OutputPin[] out;

  public final int size;

  @NotNull
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

  @NotNull
  public OutputPin out(final int pin) {
    return this.out[pin];
  }

  public void connect(final Transceiver transceiver, final TransceiverSide side) {
    final InputPin[] in = new InputPin[this.size];

    for(int pin = 0; pin < this.size; pin++) {
      final int pin1 = pin;
      in[pin] = new InputPin(state -> {
        if(state != PinState.DISCONNECTED) {
          this.out[pin1].setState(state);
        }
      });
      in[pin].connectTo(transceiver.out(side, pin));
      transceiver.in(side, pin).connectTo(this.out(pin));
    }

    this.in.put(transceiver, in);
  }
}

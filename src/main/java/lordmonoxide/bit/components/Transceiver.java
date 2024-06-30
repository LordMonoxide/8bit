package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;

import java.util.EnumMap;
import java.util.Map;
import java.util.OptionalInt;

public class Transceiver extends Component {
  private final Map<TransceiverSide, InputConnection> in = new EnumMap<>(TransceiverSide.class);
  private final Map<TransceiverSide, OutputConnection> out = new EnumMap<>(TransceiverSide.class);

  /**
   * LOW = b->a, HIGH = a->b
   */
  public final InputConnection dir = new InputConnection(1, this::changeOutput);

  public final InputConnection enable = new InputConnection(1, this::changeOutput);

  public final int bits;

  public Transceiver(final int bits) {
    this.bits = bits;

    final OutputConnection aOut = new OutputConnection(bits).setValue(0);
    final OutputConnection bOut = new OutputConnection(bits).setValue(0);
    final InputConnection aIn = new InputConnection(bits, value -> this.changeState(bOut, value));
    final InputConnection bIn = new InputConnection(bits, value -> this.changeState(aOut, value));

    this.in.put(TransceiverSide.A, aIn);
    this.in.put(TransceiverSide.B, bIn);
    this.out.put(TransceiverSide.A, aOut);
    this.out.put(TransceiverSide.B, bOut);
  }

  public InputConnection in(final TransceiverSide side) {
    return this.in.get(side);
  }

  public OutputConnection out(final TransceiverSide side) {
    return this.out.get(side);
  }

  private void changeOutput(final OptionalInt value) {
    final OutputConnection aOut = this.out.get(TransceiverSide.A);
    final OutputConnection bOut = this.out.get(TransceiverSide.B);

    aOut.disable();
    bOut.disable();

    if(this.enable.getValue().orElseThrow(() -> new FloatingConnectionException("Transceiver enable is floating")) != 0) {
      if(this.dir.getValue().orElseThrow(() -> new FloatingConnectionException("Transceiver dir is floating")) != 0) {
        bOut.enable();
      } else {
        aOut.enable();
      }
    }
  }

  private void changeState(final OutputConnection out, final OptionalInt value) {
    out.setValue(value.getAsInt());
  }
}

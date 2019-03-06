package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class Transceiver extends Component {
  private final Map<TransceiverSide, InputPin[]> in = new EnumMap<>(TransceiverSide.class);
  private final Map<TransceiverSide, OutputPin[]> out = new EnumMap<>(TransceiverSide.class);

  /**
   * LOW = b->a, HIGH = a->b
   */
  public final InputPin dir = new InputPin(this::changeOutput);

  public final InputPin enable = new InputPin(this::changeOutput);

  public final int size;

  @NotNull
  public static Transceiver eightBit() {
    return new Transceiver(8);
  }

  public Transceiver(final int size) {
    this.size = size;

    final InputPin[] aIn = new InputPin[size];
    final InputPin[] bIn = new InputPin[size];
    final OutputPin[] aOut = new OutputPin[size];
    final OutputPin[] bOut = new OutputPin[size];

    for(int i = 0; i < size; i++) {
      final int i1 = i;
      aIn[i] = new InputPin(state -> this.changeState(aIn[i1], bOut[i1]));
      bIn[i] = new InputPin(state -> this.changeState(bIn[i1], aOut[i1]));
      aOut[i] = new OutputPin();
      bOut[i] = new OutputPin();
    }

    this.in.put(TransceiverSide.A, aIn);
    this.in.put(TransceiverSide.B, bIn);
    this.out.put(TransceiverSide.A, aOut);
    this.out.put(TransceiverSide.B, bOut);
  }

  @NotNull
  public InputPin in(final TransceiverSide side, final int pin) {
    return this.in.get(side)[pin];
  }

  @NotNull
  public OutputPin out(final TransceiverSide side, final int pin) {
    return this.out.get(side)[pin];
  }

  private void changeOutput(final PinState state) {
    if(this.dir.isDisconnected() || this.enable.isDisconnected()) {
      throw new FloatingPinException();
    }

    final OutputPin[] aOut = this.out.get(TransceiverSide.A);
    final OutputPin[] bOut = this.out.get(TransceiverSide.B);

    for(int i = 0; i < this.size; i++) {
      aOut[i].disable();
      bOut[i].disable();
    }

    if(this.enable.isHigh()) {
      if(this.dir.isHigh()) {
        for(int i = 0; i < this.size; i++) {
          bOut[i].enable();
        }
      }

      if(this.dir.isLow()) {
        for(int i = 0; i < this.size; i++) {
          aOut[i].enable();
        }
      }
    }
  }

  private void changeState(final InputPin in, final OutputPin out) {
    if(this.dir.isDisconnected()) {
      throw new FloatingPinException();
    }

    out.setState(in.getState());
  }
}

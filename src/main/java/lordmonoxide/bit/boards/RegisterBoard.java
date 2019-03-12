package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;

public class RegisterBoard extends Board {
  public final String name;
  private final Register register;

  public final InputPin enable;
  public final InputPin clock;
  public final InputPin input;
  public final InputPin output;

  public RegisterBoard(final String name, final int size) {
    super(size);

    this.name = name;

    this.register = new Register(size);
    this.enable = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " EN " + state)), this.getTransceiver().enable);
    this.clock = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " CLK " + state)), this.register.clock);

    this.input = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " IN " + state)), this.register.load, new InputPin(state -> {
      if(state == PinState.HIGH) {
        this.getTransceiver().dir.setHigh();
      }
    }));

    this.output = new InputPin(state -> {
      System.out.println(this.name + " OUT " + state);

      if(state == PinState.HIGH) {
        this.getTransceiver().dir.setLow();
      }
    });

    for(int i = 0; i < this.size; i++) {
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.register.out(i));
      this.register.in(i).connectTo(this.getTransceiver().out(TransceiverSide.B, i));
    }
  }

  public OutputPin out(final int pin) {
    return this.register.out(pin);
  }

  public void clear() {
    this.register.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.register;
  }
}

package lordmonoxide.bit.boards;

import lordmonoxide.bit.components.RAM;
import lordmonoxide.bit.components.TransceiverSide;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class RAMBoard extends Board {
  public final String name;
  private final RAM ram;

  public final InputPin enable;
  public final InputPin clock;
  public final InputPin input;
  public final InputPin output;

  public RAMBoard(final String name, final int addressSize, final int wordSize) {
    super(wordSize);

    this.name = name;

    this.ram = new RAM(addressSize, wordSize);
    this.enable = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " EN " + state)), this.getTransceiver().enable);
    this.clock = InputPin.aggregate(new InputPin(state -> System.out.println(this.name + " CLK "  + state)), this.ram.clock);

    this.input = InputPin.aggregate(this.ram.load, new InputPin(state -> {
      System.out.println(this.name + " IN " + state);

      if(state == PinState.HIGH) {
        this.getTransceiver().dir.connectTo(Pins.VCC);
      }
    }));

    this.output = new InputPin(state -> {
      System.out.println(this.name + " OUT " + state);

      if(state == PinState.HIGH) {
        this.getTransceiver().dir.connectTo(Pins.GND);
      }
    });

    for(int i = 0; i < this.size; i++) {
      this.getTransceiver().in(TransceiverSide.B, i).connectTo(this.ram.out(i));
      this.ram.in(i).connectTo(this.getTransceiver().out(TransceiverSide.B, i));
    }
  }

  public InputPin address(final int pin) {
    return this.ram.address(pin);
  }

  public void set(final int address, final int value) {
    this.ram.set(address, value);
  }

  public void clear() {
    this.ram.clear();
  }

  @Override
  public String toString() {
    return this.name + ": " + this.ram;
  }
}

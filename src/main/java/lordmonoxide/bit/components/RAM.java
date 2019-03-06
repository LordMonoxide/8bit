package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class RAM extends Component {
  private final int[] values;

  private final InputPin[] address;

  private final InputPin[] valueIn;
  private final OutputPin[] valueOut;

  public final InputPin load = new InputPin();
  public final InputPin clock = new InputPin(this::onClock);

  public final int addressSize;
  public final int wordSize;

  public RAM(final int addressSize, final int wordSize) {
    this.addressSize = addressSize;
    this.wordSize = wordSize;

    final int addressCount = (int)Math.pow(2, addressSize);

    this.address = new InputPin[addressSize];

    for(int i = 0; i < addressSize; i++) {
      this.address[i] = new InputPin();
    }

    this.values = new int[addressCount];

    this.valueIn = new InputPin[wordSize];
    this.valueOut = new OutputPin[wordSize];

    for(int i = 0; i < wordSize; i++) {
      this.valueIn[i] = new InputPin();
      this.valueOut[i] = new OutputPin();
    }
  }

  public InputPin address(final int pin) {
    return this.address[pin];
  }

  public InputPin in(final int pin) {
    return this.valueIn[pin];
  }

  public OutputPin out(final int pin) {
    return this.valueOut[pin];
  }

  public void clear() {
    final int addressCount = (int)Math.pow(2, addressSize);

    for(int address = 0; address < addressCount; address++) {
      this.values[address] = 0;
    }

    for(int pin = 0; pin < this.wordSize; pin++) {
      this.valueOut[pin].setLow();
    }
  }

  private void onClock(final PinState state) {
    final int address = Pins.toInt(this.address);

    if(state != PinState.HIGH) {
      return;
    }

    if(this.load.isHigh()) {
      this.values[address] = Pins.toInt(this.valueIn);
    }

    final int value = this.values[address];

    for(int pin = 0; pin < this.wordSize; pin++) {
      this.valueOut[pin].setState(Pins.fromInt(value, pin));
    }
  }

  @Override
  public String toString() {
    return "RAM: @" + Pins.toBits(this.address) + ": " + this.values[Pins.toInt(this.address)];
  }
}

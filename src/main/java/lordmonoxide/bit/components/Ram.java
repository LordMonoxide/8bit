package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;
import lordmonoxide.bit.parts.Pins;

import java.util.Arrays;
import java.util.OptionalInt;

public class Ram extends Component {
  private final int[] values;

  public final InputConnection bank;
  public final InputConnection address;

  public final InputConnection in;
  public final OutputConnection out;

  public final InputConnection load = new InputConnection(1);
  public final InputConnection clock = new InputConnection(1, this::onClock);

  public final int bits;

  public Ram(final int bits) {
    this.bits = bits;

    final int addressCount = (int)Math.pow(2, bits * 2);

    this.bank = new InputConnection(bits);
    this.address = new InputConnection(bits);

    this.values = new int[addressCount];

    this.in = new InputConnection(bits);
    this.out = new OutputConnection(bits).setValue(0);
  }

  public void set(final int address, final int value) {
    this.values[address] = value;
  }

  public int get(final int address) {
    return this.values[address];
  }

  public void clear() {
    Arrays.fill(this.values, 0);
    this.out.setValue(0);
  }

  private void onClock(final OptionalInt value) {
    if(value.getAsInt() == 0) {
      return;
    }

    final int address = this.bank.getValue().orElseThrow(() -> new FloatingConnectionException("RAM bank is floating")) << this.bits | this.address.getValue().orElseThrow(() -> new FloatingConnectionException("RAM address is floating"));

    if(this.load.getValue().orElseThrow(() -> new FloatingConnectionException("RAM load is floating")) != 0) {
      this.values[address] = this.in.getValue().orElseThrow(() -> new FloatingConnectionException("RAM in is floating"));
    }

    this.out.setValue(this.values[address]);
  }

  @Override
  public String toString() {
    return "RAM: @" + Pins.toBits(this.bank) + Pins.toBits(this.address) + ": " + this.values[this.address.getValue().getAsInt()];
  }
}

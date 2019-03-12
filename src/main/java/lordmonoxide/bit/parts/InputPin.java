package lordmonoxide.bit.parts;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class InputPin extends Pin {
  @Nullable
  private OutputPin connection;

  private final Consumer<PinState> onStateChange;

  public static InputPin aggregate(final InputPin... pins) {
    return new AggregateInputPin(pins);
  }

  public InputPin() {
    this(pin -> { });
  }

  public InputPin(final Consumer<PinState> onStateChange) {
    this.onStateChange = onStateChange;
  }

  public InputPin connectTo(final OutputPin pin) {
    final PinState oldState = this.getState();

    this.connection = pin;
    pin.onStateChange(this, this.onStateChange);

    if(oldState != this.getState()) {
      //this.onStateChange.accept(this.getState());
    }

    return this;
  }

  public InputPin disconnect() {
    final PinState oldState = this.getState();

    if(this.connection != null) {
      this.connection.removeOnStateChange(this);
    }

    this.connection = null;

    if(oldState != this.getState()) {
      this.onStateChange.accept(this.getState());
    }

    return this;
  }

  @Override
  public PinState getState() {
    if(this.connection == null) {
      return PinState.DISCONNECTED;
    }

    return this.connection.getState();
  }

  public boolean isHigh() {
    return this.getState() == PinState.HIGH;
  }

  public boolean isLow() {
    return this.getState() == PinState.LOW;
  }

  public boolean isDisconnected() {
    return this.getState() == PinState.DISCONNECTED;
  }

  @Override
  public String toString() {
    return "Input pin [" + this.getState() + ']';
  }

  public static final class AggregateInputPin extends InputPin {
    private final InputPin[] pins;

    private AggregateInputPin(final InputPin... pins) {
      this.pins = pins;
    }

    @Override
    public InputPin connectTo(final OutputPin output) {
      for(final InputPin pin : this.pins) {
        pin.connectTo(output);
      }

      return this;
    }

    @Override
    public PinState getState() {
      for(final InputPin pin : this.pins) {
        if(pin.isHigh()) {
          return PinState.HIGH;
        }
      }

      return PinState.LOW;
    }
  }
}

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
    this(state -> {});
  }

  public InputPin(final Consumer<PinState> onChangeState) {
    this.onStateChange = onChangeState;
  }

  public InputPin connectTo(final OutputPin pin) {
    this.connection = pin;
    this.connection.addConnection(this);
    return this;
  }

  public InputPin disconnect() {
    this.setState(PinState.DISCONNECTED);

    if(this.connection == null) {
      return this;
    }

    this.connection.removeConnection(this);
    this.connection = null;
    return this;
  }

  public InputPin setHigh() {
    return this.setState(PinState.HIGH);
  }

  public InputPin setLow() {
    return this.setState(PinState.LOW);
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

  public InputPin setState(final PinState state) {
    this.state = state;
    this.onStateChange.accept(this.getState());
    return this;
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
    public InputPin setHigh() {
      for(final InputPin pin : this.pins) {
        pin.setHigh();
      }

      return this;
    }

    @Override
    public InputPin setLow() {
      for(final InputPin pin : this.pins) {
        pin.setLow();
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

    @Override
    public InputPin setState(final PinState state) {
      for(final InputPin pin : this.pins) {
        pin.setState(state);
      }

      return this;
    }
  }
}

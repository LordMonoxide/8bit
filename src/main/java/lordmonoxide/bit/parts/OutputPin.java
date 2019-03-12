package lordmonoxide.bit.parts;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OutputPin extends Pin {
  private final Map<InputPin, Consumer<PinState>> onStateChange = new HashMap<>();

  private PinState state = PinState.LOW;
  private boolean disabled;

  public OutputPin onStateChange(final InputPin pin, final Consumer<PinState> onStateChange) {
    this.onStateChange.put(pin, onStateChange);
    return this;
  }

  public OutputPin removeOnStateChange(final InputPin pin) {
    this.onStateChange.remove(pin);
    return this;
  }

  public OutputPin setHigh() {
    return this.setState(PinState.HIGH);
  }

  public OutputPin setLow() {
    return this.setState(PinState.LOW);
  }

  @Override
  public PinState getState() {
    return this.disabled ? PinState.DISCONNECTED : this.state;
  }

  public OutputPin disable() {
    this.disabled = true;
    this.onStateChange.forEach((pin, callback) -> callback.accept(this.getState()));
    return this;
  }

  public OutputPin enable() {
    this.disabled = false;
    this.onStateChange.forEach((pin, callback) -> callback.accept(this.getState()));
    return this;
  }

  public OutputPin setState(final PinState state) {
    this.state = state;

    if(!this.disabled) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getState()));
    }

    return this;
  }

  @Override
  public String toString() {
    return "Output pin [" + this.getState() + ']';
  }
}

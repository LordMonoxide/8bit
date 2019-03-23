package lordmonoxide.bit.parts;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OutputPin extends Pin {
  private static boolean enableStateCallbacks = true;

  public static void enableStateCallbacks() {
    enableStateCallbacks = true;
  }

  public static void disableStateCallbacks() {
    enableStateCallbacks = false;
  }

  private final Map<InputPin, Consumer<PinState>> onStateChange = new LinkedHashMap<>();

  private PinState state = PinState.LOW;
  private boolean disabled;

  public OutputPin onStateChange(final InputPin pin, final Consumer<PinState> onStateChange) {
    this.onStateChange.put(pin, onStateChange);

    if(enableStateCallbacks) {
      onStateChange.accept(this.getState());
    }

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

    if(enableStateCallbacks) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getState()));
    }

    return this;
  }

  public OutputPin enable() {
    this.disabled = false;

    if(enableStateCallbacks) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getState()));
    }

    return this;
  }

  public OutputPin setState(final PinState state) {
    this.state = state;

    if(!this.disabled && enableStateCallbacks) {
      this.onStateChange.forEach((pin, callback) -> callback.accept(this.getState()));
    }

    return this;
  }

  @Override
  public String toString() {
    return "Output pin [" + this.getState() + ']';
  }
}

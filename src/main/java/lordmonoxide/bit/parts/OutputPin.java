package lordmonoxide.bit.parts;

import java.util.ArrayList;
import java.util.List;

public class OutputPin extends Pin {
  private final List<InputPin> connections = new ArrayList<>();

  private boolean disabled;

  void addConnection(final InputPin pin) {
    this.connections.add(pin);
    pin.setState(this.getState());
  }

  void removeConnection(final InputPin pin) {
    this.connections.remove(pin);
  }

  public OutputPin setHigh() {
    return this.setState(PinState.HIGH);
  }

  public OutputPin setLow() {
    return this.setState(PinState.LOW);
  }

  @Override
  public PinState getState() {
    return this.disabled ? PinState.DISCONNECTED : super.getState();
  }

  public OutputPin disable() {
    this.disabled = true;
    this.connections.forEach(pin -> pin.setState(PinState.DISCONNECTED));
    return this;
  }

  public OutputPin enable() {
    this.disabled = false;
    this.connections.forEach(pin -> pin.setState(this.getState()));
    return this;
  }

  public OutputPin setState(final PinState state) {
    this.state = state;

    if(!this.disabled) {
      this.connections.forEach(pin -> pin.setState(this.getState()));
    }

    return this;
  }
}

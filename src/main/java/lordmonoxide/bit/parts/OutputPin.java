package lordmonoxide.bit.parts;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OutputPin {
  @NotNull
  private final List<InputPin> connections = new ArrayList<>();

  @NotNull
  private PinState state = PinState.HIGH;

  void addConnection(@NotNull final InputPin pin) {
    this.connections.add(pin);
    pin.setState(this.state);
  }

  public OutputPin setHigh() {
    return this.setState(PinState.HIGH);
  }

  public OutputPin setLow() {
    return this.setState(PinState.LOW);
  }

  @NotNull
  public PinState getState() {
    return this.state;
  }

  public OutputPin setState(@NotNull final PinState state) {
    this.state = state;
    this.connections.forEach(pin -> pin.setState(this.state));
    return this;
  }
}

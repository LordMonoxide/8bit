package lordmonoxide.bit.parts;

import org.jetbrains.annotations.NotNull;

public abstract class Pin {
  @NotNull
  protected PinState state = PinState.HIGH;

  @NotNull
  public PinState getState() {
    return this.state;
  }
}

package lordmonoxide.bit.parts;

public abstract class Pin {
  protected PinState state = PinState.LOW;

  public PinState getState() {
    return this.state;
  }
}

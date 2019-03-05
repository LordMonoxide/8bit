package lordmonoxide.bit.parts;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class InputPin {
  @Nullable
  private OutputPin connection;

  @NotNull
  private PinState state = PinState.HIGH;

  @NotNull
  private final Consumer<PinState> onStateChange;

  public static InputPin aggregate(final InputPin... pins) {
    return new AggregateInputPin(pins);
  }

  public InputPin() {
    this(state -> {});
  }

  public InputPin(@NotNull final Consumer<PinState> onChangeState) {
    this.onStateChange = onChangeState;
  }

  public InputPin connectTo(@NotNull final OutputPin pin) {
    this.connection = pin;
    this.connection.addConnection(this);
    return this;
  }

  public InputPin setHigh() {
    return this.setState(PinState.HIGH);
  }

  public InputPin setLow() {
    return this.setState(PinState.LOW);
  }

  @NotNull
  public PinState getState() {
    return this.state;
  }

  public InputPin setState(@NotNull final PinState state) {
    this.state = state;
    this.onStateChange.accept(this.state);
    return this;
  }

  public static class AggregateInputPin extends InputPin {
    private final InputPin[] pins;

    private AggregateInputPin(final InputPin... pins) {
      this.pins = pins;
    }

    @Override
    public InputPin connectTo(final @NotNull OutputPin output) {
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
    public @NotNull PinState getState() {
      return this.pins[0].getState();
    }

    @Override
    public InputPin setState(final @NotNull PinState state) {
      for(final InputPin pin : this.pins) {
        pin.setState(state);
      }

      return this;
    }
  }
}

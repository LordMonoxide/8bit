package lordmonoxide.bit.parts;

import lordmonoxide.bit.FloatingPinException;
import org.jetbrains.annotations.NotNull;

public enum PinState {
  HIGH, LOW, DISCONNECTED;

  public int toInt() {
    return this == HIGH ? 1 : 0;
  }

  @NotNull
  public PinState and(@NotNull final PinState... states) {
    if(this == DISCONNECTED) {
      throw new FloatingPinException();
    }

    if(this != HIGH) {
      return LOW;
    }

    for(final PinState state : states) {
      if(state != HIGH) {
        return LOW;
      }
    }

    return HIGH;
  }

  @NotNull
  public PinState or(@NotNull final PinState... states) {
    if(this == DISCONNECTED) {
      throw new FloatingPinException();
    }

    if(this == HIGH) {
      return HIGH;
    }

    for(final PinState state : states) {
      if(state == HIGH) {
        return HIGH;
      }
    }

    return LOW;
  }

  @NotNull
  public PinState xor(@NotNull final PinState... states) {
    if(this == DISCONNECTED) {
      throw new FloatingPinException();
    }

    int xor = this.toInt();

    for(final PinState state : states) {
      xor ^= state.toInt();
    }

    return xor == 1 ? HIGH : LOW;
  }

  @NotNull
  public PinState not() {
    if(this == DISCONNECTED) {
      throw new FloatingPinException();
    }

    return this == HIGH ? LOW : HIGH;
  }
}

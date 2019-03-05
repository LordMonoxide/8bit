package lordmonoxide.bit.parts;

import org.jetbrains.annotations.NotNull;

public enum PinState {
  HIGH, LOW;

  @NotNull
  public PinState and(@NotNull final PinState... states) {
    if(this == LOW) {
      return LOW;
    }

    for(final PinState state : states) {
      if(state == LOW) {
        return LOW;
      }
    }

    return HIGH;
  }

  @NotNull
  public PinState or(@NotNull final PinState... states) {
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
    int xor = this == HIGH ? 1 : 0;

    for(final PinState state : states) {
      xor ^= state == HIGH ? 1 : 0;
    }

    return xor == 1 ? HIGH : LOW;
  }

  @NotNull
  public PinState not() {
    return this == HIGH ? LOW : HIGH;
  }
}

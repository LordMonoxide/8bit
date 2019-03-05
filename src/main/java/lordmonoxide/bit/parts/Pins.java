package lordmonoxide.bit.parts;

public final class Pins {
  private Pins() { }

  public static String toBits(final Pin... pins) {
    final String out = Integer.toString(toInt(pins), 2);

    final int paddedLength = (Math.floorDiv(out.length(), 8) + 1) * 8;

    if(out.length() == paddedLength) {
      return out;
    }

    return "0".repeat(paddedLength - out.length()) + out;
  }

  public static int toInt(final Pin... pins) {
    int output = 0;

    for(int i = 0; i < pins.length; i++) {
      output |= pins[i].state.toInt() << i;
    }

    return output;
  }

  public static PinState fromInt(final int value, final int index) {
    return (value & 1 << index) >> index == 1 ? PinState.HIGH : PinState.LOW;
  }
}

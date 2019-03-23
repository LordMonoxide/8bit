package lordmonoxide.bit.parts;

public final class Pins {
  public static final OutputConnection VCC = new OutputConnection(1).setValue(1);
  public static final OutputConnection GND = new OutputConnection(1).setValue(0);

  private Pins() { }

  public static String toBits(final Connection connection) {
    if(connection.getValue().isEmpty()) {
      return "disconnected";
    }

    final String out = Integer.toString(connection.getValue().getAsInt(), 2);

    final int paddedLength = (int)Math.ceil(connection.size / 8.0f) * 8;

    if(out.length() == paddedLength) {
      return out;
    }

    return "0".repeat(paddedLength - out.length()) + out;
  }
}

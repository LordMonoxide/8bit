package lordmonoxide.bit.parts;

public final class Pins {
  public static final OutputConnection VCC = new OutputConnection(1).setValue(1);
  public static final OutputConnection GND = new OutputConnection(1).setValue(0);

  private Pins() { }

  public static String toBits(final Connection connection) {
    if(connection.getValue().isEmpty()) {
      return "disconnected";
    }

    final int value = connection.getValue().getAsInt();
    final StringBuilder out = new StringBuilder();

    for(int bit = 0; bit < connection.bits; bit++) {
      out.insert(0, value >> bit & 0b1);
    }

    return out.toString();
  }
}

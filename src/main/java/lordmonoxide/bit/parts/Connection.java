package lordmonoxide.bit.parts;

import java.util.OptionalInt;

public abstract class Connection {
  public final int bits;

  public Connection(final int bits) {
    this.bits = bits;
  }

  public abstract OptionalInt getValue();
}

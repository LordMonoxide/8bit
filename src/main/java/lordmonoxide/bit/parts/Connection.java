package lordmonoxide.bit.parts;

import java.util.OptionalInt;

public abstract class Connection {
  public final int size;

  public Connection(final int size) {
    this.size = size;
  }

  public abstract OptionalInt getValue();
}

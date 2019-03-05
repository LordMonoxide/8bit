package lordmonoxide.bit.components;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.OutputPin;

public class Clock extends Component {
  public final int hz;
  public final OutputPin out = new OutputPin();

  public Clock(final int hz) {
    this.hz = hz;
  }
}

package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;

public class Clock extends Component {
  public final OutputConnection out = new OutputConnection(1).setValue(0);
  public final InputConnection halt = new InputConnection(1);
  public final int hz;
  private final int sleep;

  public Clock(final int hz) {
    this.hz = hz;
    this.sleep = 1000 / hz;
  }

  public void run() {
    while(this.halt.getValue().orElseThrow(() -> new FloatingConnectionException("Clock halt is floating")) == 0) {
      this.out.setValue(1);
      this.out.setValue(0);

      try {
        Thread.sleep(this.sleep);
      } catch(final InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

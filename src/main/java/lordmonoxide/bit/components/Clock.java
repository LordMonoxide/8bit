package lordmonoxide.bit.components;

import lordmonoxide.bit.FloatingPinException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;

public class Clock extends Component {
  public final OutputPin out = new OutputPin();
  public final InputPin halt = new InputPin();
  public final int hz;
  private final int sleep;

  public Clock(final int hz) {
    this.hz = hz;
    this.sleep = 1000 / hz;
  }

  public void run() {
    while(!this.halt.isHigh()) {
      if(this.halt.isDisconnected()) {
        throw new FloatingPinException("Clock halt is disconnected");
      }

      this.out.setHigh();
      this.out.setLow();

      try {
        Thread.sleep(this.sleep);
      } catch(final InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

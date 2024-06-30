package lordmonoxide.bit.cpu;

import lordmonoxide.bit.FloatingConnectionException;
import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputConnection;
import lordmonoxide.bit.parts.OutputConnection;

import java.util.OptionalInt;

public class Cpu extends Component {
  public final OutputConnection halt = new OutputConnection(1);
  public final OutputConnection aIn = new OutputConnection(1);
  public final OutputConnection aEnable = new OutputConnection(1);
  public final OutputConnection bIn = new OutputConnection(1);
  public final OutputConnection bEnable = new OutputConnection(1);
  public final OutputConnection aluEnable = new OutputConnection(1);
  public final OutputConnection aluSubtract = new OutputConnection(1);
  public final OutputConnection addressIn = new OutputConnection(1);
  public final OutputConnection addressEnable = new OutputConnection(1);
  public final OutputConnection bankIn = new OutputConnection(1);
  public final OutputConnection bankEnable = new OutputConnection(1);
  public final OutputConnection bankDisable = new OutputConnection(1);
  public final OutputConnection ramIn = new OutputConnection(1);
  public final OutputConnection ramEnable = new OutputConnection(1);
  public final OutputConnection countIn = new OutputConnection(1);
  public final OutputConnection countEnable = new OutputConnection(1);
  public final OutputConnection countCount = new OutputConnection(1);
  public final OutputConnection instructionIn = new OutputConnection(1);
  public final OutputConnection instructionEnable = new OutputConnection(1);
  public final OutputConnection outIn = new OutputConnection(1);
  public final OutputConnection outEnable = new OutputConnection(1);
  public final OutputConnection flagsIn = new OutputConnection(1);

  public final InputConnection flagZero = new InputConnection(1);
  public final InputConnection flagCarry = new InputConnection(1);

  public final InputConnection instruction;

  private int step;

  public final InputConnection clock = new InputConnection(1, this::onClock);

  public Cpu(final int bits) {
    this.instruction = new InputConnection(bits);
  }

  private void onClock(final OptionalInt value) {
    if(value.isEmpty()) {
      throw new FloatingConnectionException("CPU clock connection is floating");
    }

    if(value.getAsInt() == 0) {
      return;
    }

    if(this.instruction.getValue().isEmpty()) {
      throw new FloatingConnectionException("CPU instruction connection is floating");
    }

    final CpuInstructions instruction = CpuInstructions.values()[this.instruction.getValue().getAsInt()];

//    System.out.println(instruction + " " + this.step);

    CpuControls.reset(this);
    instruction.activate(this, this.step);

    this.step = (this.step + 1) % 6;
  }
}

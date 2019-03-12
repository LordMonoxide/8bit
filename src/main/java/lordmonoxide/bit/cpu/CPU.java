package lordmonoxide.bit.cpu;

import lordmonoxide.bit.parts.Component;
import lordmonoxide.bit.parts.InputPin;
import lordmonoxide.bit.parts.OutputPin;
import lordmonoxide.bit.parts.PinState;
import lordmonoxide.bit.parts.Pins;

public class CPU extends Component {
  public final OutputPin aIn = new OutputPin();
  public final OutputPin aEnable = new OutputPin();
  public final OutputPin bIn = new OutputPin();
  public final OutputPin bEnable = new OutputPin();
  public final OutputPin aluEnable = new OutputPin();
  public final OutputPin addressIn = new OutputPin();
  public final OutputPin addressEnable = new OutputPin();
  public final OutputPin bankIn = new OutputPin();
  public final OutputPin bankEnable = new OutputPin();
  public final OutputPin bankDisable = new OutputPin();
  public final OutputPin ramIn = new OutputPin();
  public final OutputPin ramOut = new OutputPin();
  public final OutputPin ramEnable = new OutputPin();
  public final OutputPin countIn = new OutputPin();
  public final OutputPin countEnable = new OutputPin();
  public final OutputPin countCount = new OutputPin();
  public final OutputPin instructionIn = new OutputPin();
  public final OutputPin instructionEnable = new OutputPin();
  public final OutputPin outIn = new OutputPin();
  public final OutputPin outEnable = new OutputPin();

  private final InputPin[] instruction;

  private int step;

  public final InputPin clock = new InputPin(this::onClock);

  public CPU(final int size) {
    this.instruction = new InputPin[size];

    for(int pin = 0; pin < size; pin++) {
      this.instruction[pin] = new InputPin();
    }
  }

  public InputPin instruction(final int pin) {
    return this.instruction[pin];
  }

  private void onClock(final PinState state) {
    System.out.println("CPU CLK " + state);

    if(state != PinState.HIGH) {
      return;
    }

    final CPUInstructions instruction = CPUInstructions.values()[Pins.toInt(this.instruction)];

    System.out.println(instruction + " " + this.step);

    CPUControls.reset(this);
    instruction.activate(this, this.step);

    this.step = (this.step + 1) % 6;
  }
}

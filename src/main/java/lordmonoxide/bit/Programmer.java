package lordmonoxide.bit;

import lordmonoxide.bit.boards.RamBoard;
import lordmonoxide.bit.cpu.CpuInstructions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Programmer {
  public static void program(final RamBoard ram, final Consumer<Programmer> programmer) {
    final Programmer p = new Programmer();
    programmer.accept(p);

    for(final Map.Entry<Integer, Instruction> entry : p.instructions.entrySet()) {
      final int address = entry.getKey();
      final Instruction instruction = entry.getValue();

      instruction.apply(ram, address);
    }
  }

  private final Map<Integer, Instruction> instructions = new HashMap<>();
  private final Map<String, Integer> marks = new HashMap<>();
  private int address;

  public Programmer set(final CpuInstructions instruction, final int... values) {
    this.set(instruction.ordinal());

    for(final int value : values) {
      this.set(value);
    }

    return this;
  }

  public Programmer set(final CpuInstructions instruction, final String label) {
    this.set(instruction.ordinal());
    this.instructions.put(this.address++, (ram, address) -> ram.set(address, this.marks.get(label)));
    return this;
  }

  public Programmer set(final int value) {
    this.instructions.put(this.address++, (ram, address) -> ram.set(address, value));
    return this;
  }

  public Programmer mark(final String label) {
    this.marks.put(label, this.address);
    return this;
  }

  public Programmer move(final int address) {
    this.address = address;
    return this;
  }

  @FunctionalInterface
  private interface Instruction {
    void apply(final RamBoard ram, final int address);
  }
}

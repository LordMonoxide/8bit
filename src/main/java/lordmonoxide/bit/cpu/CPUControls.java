package lordmonoxide.bit.cpu;

import lordmonoxide.bit.parts.OutputPin;

import java.util.function.Function;

public enum CPUControls {
  HALT(cpu -> cpu.halt),
  A_IN(cpu -> cpu.aIn),
  A_EN(cpu -> cpu.aEnable),
  B_IN(cpu -> cpu.bIn),
  B_EN(cpu -> cpu.bEnable),
  ALU_EN(cpu -> cpu.aluEnable),
  ADD_IN(cpu -> cpu.addressIn),
  ADD_EN(cpu -> cpu.addressEnable),
  BNK_IN(cpu -> cpu.bankIn),
  BNK_EN(cpu -> cpu.bankEnable),
  BNK_DIS(cpu -> cpu.bankDisable),
  RAM_IN(cpu -> cpu.ramIn),
  RAM_EN(cpu -> cpu.ramEnable),
  CNT_IN(cpu -> cpu.countIn),
  CNT_EN(cpu -> cpu.countEnable),
  CNT_CNT(cpu -> cpu.countCount),
  INST_IN(cpu -> cpu.instructionIn),
  INST_EN(cpu -> cpu.instructionEnable),
  OUT_IN(cpu -> cpu.outIn),
  OUT_EN(cpu -> cpu.outEnable),
  ;

  private final Function<CPU, OutputPin> get;

  CPUControls(final Function<CPU, OutputPin> get) {
    this.get = get;
  }

  void setHigh(final CPU cpu) {
    this.get.apply(cpu).setHigh();
  }

  void setLow(final CPU cpu) {
    this.get.apply(cpu).setLow();
  }

  static void reset(final CPU cpu) {
    for(final CPUControls control : values()) {
      control.setLow(cpu);
    }
  }
}

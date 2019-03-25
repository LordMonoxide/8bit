package lordmonoxide.bit.cpu;

import lordmonoxide.bit.parts.OutputConnection;

import java.util.function.Function;

public enum CPUControls {
  HALT(cpu -> cpu.halt),
  A_IN(cpu -> cpu.aIn),
  A_EN(cpu -> cpu.aEnable),
  B_IN(cpu -> cpu.bIn),
  B_EN(cpu -> cpu.bEnable),
  ALU_EN(cpu -> cpu.aluEnable),
  ALU_SUB(cpu -> cpu.aluSubtract),
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
  FLAGS_IN(cpu -> cpu.flagsIn),
  ;

  private final Function<CPU, OutputConnection> get;

  CPUControls(final Function<CPU, OutputConnection> get) {
    this.get = get;
  }

  void setHigh(final CPU cpu) {
    this.get.apply(cpu).setValue(1);
  }

  void setLow(final CPU cpu) {
    this.get.apply(cpu).setValue(0);
  }

  static void reset(final CPU cpu) {
    for(final CPUControls control : values()) {
      control.setLow(cpu);
    }
  }
}

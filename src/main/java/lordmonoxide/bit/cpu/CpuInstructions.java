package lordmonoxide.bit.cpu;

import java.util.function.Predicate;

public enum CpuInstructions {
  NOOP(op()),
  LDA(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.A_EN, CpuControls.A_IN, CpuControls.RAM_EN)
  ),
  LDB(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.B_EN, CpuControls.B_IN, CpuControls.RAM_EN)
  ),
  LDAI(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.A_EN, CpuControls.A_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
  ),
  LDBI(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.B_EN, CpuControls.B_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
  ),
  BNK(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.BNK_EN, CpuControls.BNK_IN, CpuControls.RAM_EN)
  ),
  BNKI(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.BNK_EN, CpuControls.BNK_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
  ),
  STA(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.A_EN, CpuControls.RAM_EN, CpuControls.RAM_IN)
  ),
  STB(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.B_EN, CpuControls.RAM_EN, CpuControls.RAM_IN)
  ),
  ADD(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.B_EN, CpuControls.B_IN, CpuControls.RAM_EN, CpuControls.FLAGS_IN)
    .step(CpuControls.A_EN, CpuControls.A_IN, CpuControls.ALU_EN)
  ),
  ADDI(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.B_EN, CpuControls.B_IN, CpuControls.RAM_EN, CpuControls.FLAGS_IN, CpuControls.CNT_CNT)
    .step(CpuControls.A_EN, CpuControls.A_IN, CpuControls.ALU_EN)
  ),
  SUB(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.RAM_EN, CpuControls.CNT_CNT)
    .step(CpuControls.B_EN, CpuControls.B_IN, CpuControls.RAM_EN, CpuControls.ALU_SUB, CpuControls.FLAGS_IN)
    .step(CpuControls.A_EN, CpuControls.A_IN, CpuControls.ALU_EN)
  ),
  SUBI(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.B_EN, CpuControls.B_IN, CpuControls.RAM_EN, CpuControls.ALU_SUB, CpuControls.FLAGS_IN, CpuControls.CNT_CNT)
    .step(CpuControls.A_EN, CpuControls.A_IN, CpuControls.ALU_EN)
  ),
  JMP(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(CpuControls.RAM_EN, CpuControls.CNT_EN, CpuControls.CNT_IN)
  ),
  JC(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(cpu -> cpu.flagCarry.getValue().getAsInt() != 0, CpuControls.RAM_EN, CpuControls.CNT_EN, CpuControls.CNT_IN)
    .step(cpu -> cpu.flagCarry.getValue().getAsInt() == 0, CpuControls.CNT_CNT)
  ),
  JZ(op()
    .step(CpuControls.ADD_EN, CpuControls.ADD_IN, CpuControls.CNT_EN)
    .step(cpu -> cpu.flagZero.getValue().getAsInt() != 0, CpuControls.RAM_EN, CpuControls.CNT_EN, CpuControls.CNT_IN)
    .step(cpu -> cpu.flagZero.getValue().getAsInt() == 0, CpuControls.CNT_CNT)
  ),
  OUT(op()
    .step(CpuControls.OUT_EN, CpuControls.OUT_IN, CpuControls.A_EN)
  ),
  HALT(op()
    .step(CpuControls.HALT)
  ),
  ;

  private final Predicate<Cpu>[] conditions;
  private final CpuControls[][] controls;

  CpuInstructions(final MicroInstruction micro) {
    this.conditions = micro.conditions;
    this.controls = micro.controls;

    for(int i = 0; i < this.controls.length; i++) {
      if(this.controls[i] == null) {
        this.controls[i] = new CpuControls[0];
      }
    }
  }

  void activate(final Cpu cpu, final int step) {
    if(this.conditions[step].test(cpu)) {
      for(final CpuControls control : this.controls[step]) {
        control.setHigh(cpu);
      }
    }
  }

  private static MicroInstruction op() {
    return new MicroInstruction();
  }

  private final static class MicroInstruction {
    private final Predicate<Cpu>[] conditions = new Predicate[6];
    private final CpuControls[][] controls = new CpuControls[6][];
    private int index;

    private MicroInstruction() {
      for(int i = 0; i < this.conditions.length; i++) {
        this.conditions[i] = cpu -> true;
      }

      this.step(CpuControls.CNT_EN, CpuControls.ADD_EN, CpuControls.ADD_IN);
      this.step(CpuControls.BNK_DIS, CpuControls.RAM_EN, CpuControls.INST_EN, CpuControls.INST_IN, CpuControls.CNT_CNT);
    }

    private MicroInstruction step(final Predicate<Cpu> condition, final CpuControls... controls) {
      this.conditions[this.index] = condition;
      this.controls[this.index] = controls;
      this.index++;
      return this;
    }

    private MicroInstruction step(final CpuControls... controls) {
      return this.step(cpu -> true, controls);
    }
  }
}

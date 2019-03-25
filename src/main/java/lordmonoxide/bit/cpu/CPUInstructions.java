package lordmonoxide.bit.cpu;

import java.util.function.Predicate;

public enum CPUInstructions {
  NOOP(op()),
  LDA(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.RAM_EN)
  ),
  LDB(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN)
  ),
  LDAI(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
  ),
  LDBI(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
  ),
  BNK(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
    .step(CPUControls.BNK_EN, CPUControls.BNK_IN, CPUControls.RAM_EN)
  ),
  BNKI(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.BNK_EN, CPUControls.BNK_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
  ),
  STA(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.A_EN, CPUControls.RAM_EN, CPUControls.RAM_IN, CPUControls.CNT_CNT)
  ),
  STB(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.B_EN, CPUControls.RAM_EN, CPUControls.RAM_IN, CPUControls.CNT_CNT)
  ),
  ADD(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN, CPUControls.FLAGS_IN)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.ALU_EN)
  ),
  ADDI(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN, CPUControls.FLAGS_IN, CPUControls.CNT_CNT)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.ALU_EN)
  ),
  SUB(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.CNT_CNT)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN, CPUControls.ALU_SUB, CPUControls.FLAGS_IN)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.ALU_EN)
  ),
  SUBI(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN, CPUControls.ALU_SUB, CPUControls.FLAGS_IN, CPUControls.CNT_CNT)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.ALU_EN)
  ),
  JMP(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.RAM_EN, CPUControls.CNT_EN, CPUControls.CNT_IN)
  ),
  JC(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(cpu -> cpu.flagCarry.getValue().getAsInt() != 0, CPUControls.RAM_EN, CPUControls.CNT_EN, CPUControls.CNT_IN)
    .step(cpu -> cpu.flagCarry.getValue().getAsInt() == 0, CPUControls.CNT_CNT)
  ),
  JZ(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(cpu -> cpu.flagZero.getValue().getAsInt() != 0, CPUControls.RAM_EN, CPUControls.CNT_EN, CPUControls.CNT_IN)
    .step(cpu -> cpu.flagZero.getValue().getAsInt() == 0, CPUControls.CNT_CNT)
  ),
  OUT(op()
    .step(CPUControls.OUT_EN, CPUControls.OUT_IN, CPUControls.A_EN)
  ),
  HALT(op()
    .step(CPUControls.HALT)
  ),
  ;

  private final Predicate<CPU>[] conditions;
  private final CPUControls[][] controls;

  CPUInstructions(final MicroInstruction micro) {
    this.conditions = micro.conditions;
    this.controls = micro.controls;

    for(int i = 0; i < this.controls.length; i++) {
      if(this.controls[i] == null) {
        this.controls[i] = new CPUControls[0];
      }
    }
  }

  void activate(final CPU cpu, final int step) {
    if(this.conditions[step].test(cpu)) {
      for(final CPUControls control : this.controls[step]) {
        control.setHigh(cpu);
      }
    }
  }

  private static MicroInstruction op() {
    return new MicroInstruction();
  }

  private final static class MicroInstruction {
    private final Predicate<CPU>[] conditions = new Predicate[6];
    private final CPUControls[][] controls = new CPUControls[6][];
    private int index;

    private MicroInstruction() {
      for(int i = 0; i < this.conditions.length; i++) {
        this.conditions[i] = cpu -> true;
      }

      this.step(CPUControls.CNT_EN, CPUControls.ADD_EN, CPUControls.ADD_IN);
      this.step(CPUControls.BNK_DIS, CPUControls.RAM_EN, CPUControls.INST_EN, CPUControls.INST_IN, CPUControls.CNT_CNT);
    }

    private MicroInstruction step(final Predicate<CPU> condition, final CPUControls... controls) {
      this.conditions[this.index] = condition;
      this.controls[this.index] = controls;
      this.index++;
      return this;
    }

    private MicroInstruction step(final CPUControls... controls) {
      return this.step(cpu -> true, controls);
    }
  }
}

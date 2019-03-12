package lordmonoxide.bit.cpu;

public enum CPUInstructions {
  NOOP(op()),
  LDA(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.RAM_OUT, CPUControls.CNT_CNT)
    .step(CPUControls.A_EN, CPUControls.A_IN, CPUControls.RAM_EN, CPUControls.RAM_OUT)
  ),
  ADD(op()
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.CNT_EN)
    .step(CPUControls.ADD_EN, CPUControls.ADD_IN, CPUControls.RAM_EN, CPUControls.RAM_OUT, CPUControls.CNT_CNT)
    .step(CPUControls.B_EN, CPUControls.B_IN, CPUControls.RAM_EN, CPUControls.RAM_OUT)
    .step(CPUControls.ADD_EN, CPUControls.A_IN, CPUControls.ALU_EN)
  ),
  OUT(op()
    .step(CPUControls.OUT_EN, CPUControls.OUT_IN, CPUControls.A_EN)
  ),
  TEST(op()
    .step(CPUControls.CNT_EN, CPUControls.ADD_EN, CPUControls.ADD_IN)
    .step(CPUControls.CNT_CNT)
  ),
  ;

  private final CPUControls[][] controls;

  CPUInstructions(final MicroInstruction micro) {
    this.controls = micro.controls;

    for(int i = 0; i < this.controls.length; i++) {
      if(this.controls[i] == null) {
        this.controls[i] = new CPUControls[0];
      }
    }
  }

  void activate(final CPU cpu, final int step) {
    for(final CPUControls control : this.controls[step]) {
      control.setHigh(cpu);
    }
  }

  private static MicroInstruction op() {
    return new MicroInstruction();
  }

  private final static class MicroInstruction {
    private CPUControls[][] controls = new CPUControls[6][];
    private int index;

    private MicroInstruction() {
      this.step(CPUControls.CNT_EN, CPUControls.ADD_EN, CPUControls.ADD_IN);
      this.step(CPUControls.BNK_DIS, CPUControls.RAM_EN, CPUControls.RAM_OUT, CPUControls.INST_EN, CPUControls.INST_IN, CPUControls.CNT_CNT);
    }

    private MicroInstruction step(final CPUControls... controls) {
      this.controls[this.index] = controls;
      this.index++;
      return this;
    }
  }
}

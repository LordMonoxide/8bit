package lordmonoxide.bit;

import lordmonoxide.bit.boards.ALUBoard;
import lordmonoxide.bit.boards.Bus;
import lordmonoxide.bit.boards.CounterBoard;
import lordmonoxide.bit.boards.DisablableRegisterBoard;
import lordmonoxide.bit.boards.OutputBoard;
import lordmonoxide.bit.boards.RAMBoard;
import lordmonoxide.bit.boards.RegisterBoard;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.cpu.CPU;
import lordmonoxide.bit.cpu.CPUInstructions;
import lordmonoxide.bit.parts.OutputConnection;

public class Computer {
  public static void main(final String[] args) {
    OutputConnection.disableStateCallbacks();

    final Bus bus = Bus.eightBit();

    // CLOCK SETUP
    final Clock clock = new Clock(200);

    final CPU cpu = new CPU(8);
    cpu.clock.connectTo(clock.out);

    // A REGISTER SETUP
    final RegisterBoard registerA = new RegisterBoard("A register", 8);
    registerA.clock.connectTo(clock.out);

    bus.connect(registerA);

    // B REGISTER SETUP
    final RegisterBoard registerB = new RegisterBoard("B register", 8);
    registerB.clock.connectTo(clock.out);

    bus.connect(registerB);

    // ALU SETUP
    final ALUBoard alu = new ALUBoard("ALU", 8);

    alu.a.connectTo(registerA.out);
    alu.b.connectTo(registerB.out);

    bus.connect(alu);

    // OUTPUT SETUP
    final OutputBoard output = new OutputBoard("Output register", 8);
    output.clock.connectTo(clock.out);
    bus.connect(output);

    // ADDRESS REGISTER
    final RegisterBoard address = new RegisterBoard("Address register", 8);
    address.clock.connectTo(clock.out);
    bus.connect(address);

    // BANK REGISTER
    final DisablableRegisterBoard bank = new DisablableRegisterBoard("Bank register", 8);
    bank.clock.connectTo(clock.out);
    bus.connect(bank);

    // RAM SETUP
    final RAMBoard ram = new RAMBoard("RAM", 8);
    ram.clock.connectTo(clock.out);
    bus.connect(ram);

    ram.bank.connectTo(bank.out);
    ram.address.connectTo(address.out);

    // PROGRAM COUNTER
    final CounterBoard counter = new CounterBoard("Program counter", 8);
    counter.clock.connectTo(clock.out);
    bus.connect(counter);

    // INSTRUCTION REGISTER
    final RegisterBoard instruction = new RegisterBoard("Instruction register", 8);
    instruction.clock.connectTo(clock.out);
    bus.connect(instruction);

    // FLAGS REGISTER
    final Register flags = new Register(2);
    flags.clock.connectTo(clock.out);
    flags.in.connectTo(OutputConnection.combine(alu.zero, alu.carry));

    // CPU SETUP
    clock.halt.connectTo(cpu.halt);
    registerA.input.connectTo(cpu.aIn);
    registerA.enable.connectTo(cpu.aEnable);
    registerB.input.connectTo(cpu.bIn);
    registerB.enable.connectTo(cpu.bEnable);
    alu.enable.connectTo(cpu.aluEnable);
    alu.sub.connectTo(cpu.aluSubtract);
    address.input.connectTo(cpu.addressIn);
    address.enable.connectTo(cpu.addressEnable);
    bank.input.connectTo(cpu.bankIn);
    bank.enable.connectTo(cpu.bankEnable);
    bank.disable.connectTo(cpu.bankDisable);
    ram.input.connectTo(cpu.ramIn);
    ram.enable.connectTo(cpu.ramEnable);
    counter.input.connectTo(cpu.countIn);
    counter.enable.connectTo(cpu.countEnable);
    counter.count.connectTo(cpu.countCount);
    instruction.input.connectTo(cpu.instructionIn);
    instruction.enable.connectTo(cpu.instructionEnable);
    output.input.connectTo(cpu.outIn);
    output.enable.connectTo(cpu.outEnable);
    flags.load.connectTo(cpu.flagsIn);

    cpu.flagZero.connectTo(new OutputConnection.OutputSplitterConnection(flags.out, 0));
    cpu.flagCarry.connectTo(new OutputConnection.OutputSplitterConnection(flags.out, 1));

    cpu.instruction.connectTo(instruction.out);

    OutputConnection.enableStateCallbacks();

    // PROGRAM
    ram.set(0, CPUInstructions.LDA.ordinal());
    ram.set(1, 255);
    ram.set(2, CPUInstructions.ADDI.ordinal());
    ram.set(3, 1);
    ram.set(4, CPUInstructions.STA.ordinal());
    ram.set(5, 255);
    ram.set(6, CPUInstructions.OUT.ordinal());
    ram.set(7, CPUInstructions.JC.ordinal());
    ram.set(8, 11);
    ram.set(9, CPUInstructions.JMP.ordinal());
    ram.set(10, 2);
    ram.set(11, CPUInstructions.HALT.ordinal());
    ram.set(255, 0);

    System.out.println("STARTING");
    clock.run();
  }
}

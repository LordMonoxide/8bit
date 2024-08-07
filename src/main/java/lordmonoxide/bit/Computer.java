package lordmonoxide.bit;

import lordmonoxide.bit.boards.AluBoard;
import lordmonoxide.bit.boards.Bus;
import lordmonoxide.bit.boards.CounterBoard;
import lordmonoxide.bit.boards.DisablableRegisterBoard;
import lordmonoxide.bit.boards.OutputBoard;
import lordmonoxide.bit.boards.RamBoard;
import lordmonoxide.bit.boards.RegisterBoard;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.cpu.Cpu;
import lordmonoxide.bit.cpu.CpuInstructions;
import lordmonoxide.bit.parts.OutputConnection;

public class Computer {
  public static void main(final String[] args) {
    OutputConnection.disableStateCallbacks();

    final Bus bus = Bus.eightBit();

    // CLOCK SETUP
    final Clock clock = new Clock(500);

    final Cpu cpu = new Cpu(8);
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
    final AluBoard alu = new AluBoard("ALU", 8);

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
    final RamBoard ram = new RamBoard("RAM", 8);
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
    Programmer.program(ram, Computer::fibonacci);

    System.out.println("STARTING");
    clock.run();
  }

  private static void count(final Programmer programmer) {
    programmer
      .mark("loop")                       // Mark the start of the loop
      .set(CpuInstructions.LDA, "varA")   // Load variable "varA" into register A
      .set(CpuInstructions.OUT)           // Output the value
      .set(CpuInstructions.ADDI, 1)       // Add 1 to register A
      .set(CpuInstructions.STA, "varA")   // Store the value of register A into variable "varA"
      .set(CpuInstructions.JC, "exit")    // Jump to the "exit" mark if the value rolls over from 255 to 0
      .set(CpuInstructions.JMP, "loop")   // Jump back up to the "loop" mark

      .mark("exit")                       // Mark the exit point
      .set(CpuInstructions.HALT)          // Halt execution

      .mark("varA")                       // Mark variable "varA"
      .set(0)                             // Initialize "varA" to 0
    ;
  }

  private static void fibonacci(final Programmer programmer) {
    programmer
      .set(CpuInstructions.LDA, "varA")       // Load and display the first two numbers
      .set(CpuInstructions.OUT)               //
      .set(CpuInstructions.LDA, "varB")       //
      .set(CpuInstructions.OUT)               //

      .mark("loop")
      .set(CpuInstructions.LDA, "varA")       // Load "varA" into register A
      .set(CpuInstructions.ADD, "varB")       // Load "varB" into register B, load sum (regA+regB) into register A
      .set(CpuInstructions.JC, "overflow")    // Jump out of the loop if we overflow
      .set(CpuInstructions.OUT)               // Display the value in register A
      .set(CpuInstructions.STB, "varA")       // Store register B in "varA"
      .set(CpuInstructions.STA, "varB")       // Store register A in "varB"
      .set(CpuInstructions.JMP, "loop")       // Jump back up to the "loop" mark

      .mark("overflow")
      .set(CpuInstructions.HALT)              // Stop execution

      .mark("varA")
      .set(0)                                 // Initialize "varA"

      .mark("varB")
      .set(1)                                 // Initialize "varB"
    ;
  }
}

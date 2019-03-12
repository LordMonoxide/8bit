package lordmonoxide.bit;

import lordmonoxide.bit.boards.ALUBoard;
import lordmonoxide.bit.boards.Bus;
import lordmonoxide.bit.boards.CounterBoard;
import lordmonoxide.bit.boards.DisablableRegisterBoard;
import lordmonoxide.bit.boards.OutputBoard;
import lordmonoxide.bit.boards.RAMBoard;
import lordmonoxide.bit.boards.RegisterBoard;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.cpu.CPU;
import lordmonoxide.bit.cpu.CPUInstructions;

public class Computer {
  public static void main(final String[] args) {
    final Bus bus = Bus.eightBit();

    // CLOCK SETUP
    final Clock clock = new Clock(10);

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

    for(int pin = 0; pin < 8; pin++) {
      alu.a(pin).connectTo(registerA.out(pin));
      alu.b(pin).connectTo(registerB.out(pin));
    }

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
    final RAMBoard ram = new RAMBoard("RAM", 16, 8);
    ram.clock.connectTo(clock.out);
    bus.connect(ram);

    for(int pin = 0; pin < 8; pin++) {
      ram.address(pin).connectTo(address.out(pin));
      ram.address(pin + 8).connectTo(bank.out(pin));
    }

    // PROGRAM COUNTER
    final CounterBoard counter = new CounterBoard("Program counter", 8);
    counter.clock.connectTo(clock.out);
    bus.connect(counter);

    // INSTRUCTION REGISTER
    final RegisterBoard instruction = new RegisterBoard("Instruction register", 8);
    instruction.clock.connectTo(clock.out);
    bus.connect(instruction);

    // CPU SETUP
    clock.halt.connectTo(cpu.halt);
    registerA.input.connectTo(cpu.aIn);
    registerA.enable.connectTo(cpu.aEnable);
    registerB.input.connectTo(cpu.bIn);
    registerB.enable.connectTo(cpu.bEnable);
    alu.enable.connectTo(cpu.aluEnable);
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

    for(int pin = 0; pin < 8; pin++) {
      cpu.instruction(pin).connectTo(instruction.out(pin));
    }

    // PROGRAM
    ram.set(0, CPUInstructions.LDA.ordinal());
    ram.set(1, 255);
    ram.set(2, CPUInstructions.OUT.ordinal());
    ram.set(3, CPUInstructions.HALT.ordinal());
    ram.set(255, 123);

    System.out.println("STARTING");

    clock.run();

    // DATA

/*
    address.enable.setHigh();
    address.input.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    address.input.setLow();
    address.enable.setLow();

    bank.enable.setHigh();
    bank.input.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    bank.input.setLow();
    bank.enable.setLow();

    bus.out(0).setHigh();

    ram.input.setHigh();
    ram.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    ram.enable.setLow();
    ram.input.setLow();

    System.out.println(ram);

    address.input.setHigh();
    address.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    address.enable.setLow();
    address.input.setLow();

    bank.input.setHigh();
    bank.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    bank.enable.setLow();
    bank.input.setLow();

    bus.out(0).setLow();
    bus.out(1).setHigh();

    ram.input.setHigh();
    ram.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    ram.enable.setLow();
    ram.input.setLow();

    bus.out(1).setLow();

    System.out.println(ram);

    address.input.setHigh();
    address.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    address.enable.setLow();
    address.input.setLow();

    bank.input.setHigh();
    bank.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    bank.enable.setLow();
    bank.input.setLow();

    System.out.println(ram);

    ram.enable.setHigh();
    ram.output.setHigh();

    System.out.println(bus);
*/

/*
    registerA.input.setHigh();
    registerA.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    registerA.enable.setLow();
    registerA.input.setLow();

    bus.out(0).setHigh();

    registerB.input.setHigh();
    registerB.enable.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    registerB.enable.setLow();
    registerB.input.setLow();

    do {
      output.input.setHigh();
      output.enable.setHigh();
      alu.enable.setHigh();
      registerA.enable.setHigh();
      registerA.input.setHigh();

      clock.out.setHigh();
      clock.out.setLow();

      registerA.input.setLow();
      registerA.enable.setLow();
      alu.enable.setLow();
      output.enable.setLow();
      output.input.setLow();

      Thread.sleep(500);
    } while(true);
*/
  }
}

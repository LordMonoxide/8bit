package lordmonoxide.bit;

import lordmonoxide.bit.boards.ALUBoard;
import lordmonoxide.bit.boards.OutputBoard;
import lordmonoxide.bit.boards.RAMBoard;
import lordmonoxide.bit.boards.RegisterBoard;
import lordmonoxide.bit.components.Bus;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.components.TransceiverSide;
import org.jetbrains.annotations.NotNull;

public class Computer {
  public static void main(@NotNull final String[] args) throws InterruptedException {
    System.out.println("STARTING");

    final Bus bus = Bus.eightBit();

    // CLOCK SETUP
    final Clock clock = new Clock(1);

    // A REGISTER SETUP
    final RegisterBoard registerA = new RegisterBoard(8);
    registerA.clock.connectTo(clock.out);

    bus.connect(registerA.transceiver, TransceiverSide.A);

    // B REGISTER SETUP
    final RegisterBoard registerB = new RegisterBoard(8);
    registerB.clock.connectTo(clock.out);

    bus.connect(registerB.transceiver, TransceiverSide.A);

    // ALU SETUP
    final ALUBoard alu = new ALUBoard(8);

    for(int i = 0; i < 8; i++) {
      alu.a(i).connectTo(registerA.out(i));
      alu.b(i).connectTo(registerB.out(i));
    }

    bus.connect(alu.transceiver, TransceiverSide.A);

    // OUTPUT SETUP
    final OutputBoard output = new OutputBoard(8);
    output.clock.connectTo(clock.out);
    bus.connect(output.transceiver, TransceiverSide.A);

    // ADDRESS REGISTER
    final RegisterBoard address = new RegisterBoard(8);
    address.clock.connectTo(clock.out);
    address.transceiver.dir.setHigh();
    bus.connect(address.transceiver, TransceiverSide.A);

    // BANK REGISTER
    final RegisterBoard bank = new RegisterBoard(8);
    bank.clock.connectTo(clock.out);
    bank.transceiver.dir.setHigh();
    bus.connect(bank.transceiver, TransceiverSide.A);

    // RAM SETUP
    final RAMBoard ram = new RAMBoard(16, 8);
    ram.clock.connectTo(clock.out);
    bus.connect(ram.transceiver, TransceiverSide.A);

    for(int i = 0; i < 8; i++) {
      ram.address(i).connectTo(address.out(i));
      ram.address(i + 8).connectTo(bank.out(i));
    }

    // DATA

    address.transceiver.enable.setHigh();
    address.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    address.load.setLow();
    address.transceiver.enable.setLow();

    bank.transceiver.enable.setHigh();
    bank.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    bank.load.setLow();
    bank.transceiver.enable.setLow();

    bus.out(0).setHigh();

    ram.transceiver.dir.setHigh();
    ram.transceiver.enable.setHigh();
    ram.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    ram.load.setLow();
    ram.transceiver.enable.setLow();

    System.out.println(ram);

    address.transceiver.enable.setHigh();
    address.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    address.load.setLow();
    address.transceiver.enable.setLow();

    bank.transceiver.enable.setHigh();
    bank.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    bank.load.setLow();
    bank.transceiver.enable.setLow();

    bus.out(0).setLow();
    bus.out(1).setHigh();

    ram.transceiver.dir.setHigh();
    ram.transceiver.enable.setHigh();
    ram.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    ram.load.setLow();
    ram.transceiver.enable.setLow();

    bus.out(1).setLow();

    System.out.println(ram);

    address.transceiver.enable.setHigh();
    address.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    address.load.setLow();
    address.transceiver.enable.setLow();

    bank.transceiver.enable.setHigh();
    bank.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    bank.load.setLow();
    bank.transceiver.enable.setLow();

    System.out.println(ram);

    ram.transceiver.dir.setLow();
    ram.transceiver.enable.setHigh();

    System.out.println(bus);

/*
    registerA.transceiver.dir.setHigh();
    registerA.transceiver.enable.setHigh();
    registerA.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    registerA.load.setLow();
    registerA.transceiver.enable.setLow();

    bus.out(0).setHigh();

    registerB.transceiver.dir.setHigh();
    registerB.transceiver.enable.setHigh();
    registerB.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    registerB.load.setLow();
    registerB.transceiver.enable.setLow();

    do {
      output.transceiver.enable.setHigh();
      output.load.setHigh();
      alu.transceiver.enable.setHigh();
      registerA.transceiver.enable.setHigh();
      registerA.load.setHigh();

      clock.out.setHigh();
      clock.out.setLow();

      registerA.load.setLow();
      registerA.transceiver.enable.setLow();
      alu.transceiver.enable.setLow();
      output.load.setLow();
      output.transceiver.enable.setLow();

      Thread.sleep(500);
    } while(true);
*/
  }
}

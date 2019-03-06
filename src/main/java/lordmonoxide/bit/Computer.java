package lordmonoxide.bit;

import lordmonoxide.bit.boards.ALUBoard;
import lordmonoxide.bit.boards.OutputBoard;
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
    final RegisterBoard registerA = new RegisterBoard();
    registerA.register.clock.connectTo(clock.out);

    bus.connect(registerA.transceiver, TransceiverSide.A);

    // B REGISTER SETUP
    final RegisterBoard registerB = new RegisterBoard();
    registerB.register.clock.connectTo(clock.out);

    bus.connect(registerB.transceiver, TransceiverSide.A);

    // ALU SETUP
    final ALUBoard alu = new ALUBoard();

    for(int i = 0; i < alu.alu.size; i++) {
      alu.alu.a(i).connectTo(registerA.register.out(i));
      alu.alu.b(i).connectTo(registerB.register.out(i));
    }

    bus.connect(alu.transceiver, TransceiverSide.A);

    // OUTPUT SETUP
    final OutputBoard output = new OutputBoard();
    output.clock.connectTo(clock.out);
    bus.connect(output.transceiver, TransceiverSide.A);

    // DATA

    registerA.transceiver.dir.setHigh();
    registerA.transceiver.enable.setHigh();
    registerA.register.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    registerA.register.load.setLow();
    registerA.transceiver.enable.setLow();

    bus.out(0).setHigh();

    registerB.transceiver.dir.setHigh();
    registerB.transceiver.enable.setHigh();
    registerB.register.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    registerB.register.load.setLow();
    registerB.transceiver.enable.setLow();

    do {
      output.transceiver.enable.setHigh();
      output.load.setHigh();
      alu.transceiver.enable.setHigh();
      registerA.transceiver.enable.setHigh();
      registerA.register.load.setHigh();

      clock.out.setHigh();
      clock.out.setLow();

      registerA.register.load.setLow();
      registerA.transceiver.enable.setLow();
      alu.transceiver.enable.setLow();
      output.load.setLow();
      output.transceiver.enable.setLow();

      Thread.sleep(500);
    } while(true);
  }
}

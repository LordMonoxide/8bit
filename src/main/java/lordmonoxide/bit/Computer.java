package lordmonoxide.bit;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.components.Counter;
import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.Transceiver;
import org.jetbrains.annotations.NotNull;

public class Computer {
  public static void main(@NotNull final String[] args) throws InterruptedException {
    System.out.println("STARTING");

    final Clock clock = new Clock(1);

    final Counter counter = Counter.eightBit();
    counter.clock.connectTo(clock.out);
    counter.load.setLow();
    counter.clear();

    for(int i = 0; i < counter.size; i++) {
      counter.in(i).setLow();
    }

    System.out.println(counter.toBits() + ", " + counter.toInt());

    counter.in(4).setHigh();
    counter.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    counter.load.setLow();

    System.out.println(counter.toBits() + ", " + counter.toInt());

    do {
      clock.out.setHigh();
      clock.out.setLow();

      System.out.println(counter.toBits() + ", " + counter.toInt());

      Thread.sleep(100);
    } while(true);
  }

  public static void main2(@NotNull final String[] args) throws InterruptedException {
    System.out.println("STARTING");

    final Clock clock = new Clock(1);

    final Register a = Register.eightBit();
    final Register b = Register.eightBit();

    a.clock.connectTo(clock.out);
    b.clock.connectTo(clock.out);

    final ALU alu = ALU.eightBit();
    alu.carryIn.setLow();

    for(int i = 0; i < alu.size; i++) {
      alu.a(i).connectTo(a.out(i));
      alu.b(i).connectTo(b.out(i));
    }

    // Play with some data

    for(int i = 0; i < a.size; i++) {
      a.in(i).setLow();
    }

    a.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    a.load.setLow();

    for(int i = 0; i < b.size; i++) {
      b.in(i).setLow();
    }

    b.in(0).setHigh();

    b.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    b.load.setLow();

    System.out.println("A: " + a.toBits() + ", " + a.toInt());
    System.out.println("B: " + b.toBits() + ", " + b.toInt());
    System.out.println("ALU: " + alu.toBits() + ", " + alu.toInt());

    final Transceiver transceiver = Transceiver.eightBit();
    transceiver.dir.setHigh();
    transceiver.enable.setLow();

    for(int i = 0; i < transceiver.size; i++ ) {
      transceiver.aIn(i).connectTo(alu.out(i));
      a.in(i).connectTo(transceiver.aOut(i));
    }

    transceiver.enable.setHigh();

    System.out.println("A: " + a.toBits() + ", " + a.toInt());
    System.out.println("B: " + b.toBits() + ", " + b.toInt());
    System.out.println("ALU: " + alu.toBits() + ", " + alu.toInt());

    do {
      a.load.setHigh();
      clock.out.setHigh();
      clock.out.setLow();
      a.load.setLow();

      System.out.println("A: " + a.toBits() + ", " + a.toInt());
      System.out.println("B: " + b.toBits() + ", " + b.toInt());
      System.out.println("ALU: " + alu.toBits() + ", " + alu.toInt());

      Thread.sleep(500);
    } while(true);
  }
}

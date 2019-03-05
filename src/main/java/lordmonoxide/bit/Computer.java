package lordmonoxide.bit;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.components.Register;
import lordmonoxide.bit.components.Transceiver;
import org.jetbrains.annotations.NotNull;

public class Computer {
  public static void main(@NotNull final String[] args) throws InterruptedException {
    System.out.println("STARTING");

    final Clock clock = new Clock(1);

    final Register a = new Register();
    final Register b = new Register();

    a.clock.connectTo(clock.out);
    b.clock.connectTo(clock.out);

    final ALU alu = new ALU();
    alu.carryIn.setLow();
    alu.a0.connectTo(a.out0);
    alu.a1.connectTo(a.out1);
    alu.a2.connectTo(a.out2);
    alu.a3.connectTo(a.out3);
    alu.a4.connectTo(a.out4);
    alu.a5.connectTo(a.out5);
    alu.a6.connectTo(a.out6);
    alu.a7.connectTo(a.out7);
    alu.b0.connectTo(b.out0);
    alu.b1.connectTo(b.out1);
    alu.b2.connectTo(b.out2);
    alu.b3.connectTo(b.out3);
    alu.b4.connectTo(b.out4);
    alu.b5.connectTo(b.out5);
    alu.b6.connectTo(b.out6);
    alu.b7.connectTo(b.out7);

    a.in0.setLow();
    a.in1.setLow();
    a.in2.setLow();
    a.in3.setLow();
    a.in4.setLow();
    a.in5.setLow();
    a.in6.setLow();
    a.in7.setLow();

    a.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    a.load.setLow();

    b.in0.setHigh();
    b.in1.setLow();
    b.in2.setLow();
    b.in3.setLow();
    b.in4.setLow();
    b.in5.setLow();
    b.in6.setLow();
    b.in7.setLow();

    b.load.setHigh();
    clock.out.setHigh();
    clock.out.setLow();
    b.load.setLow();

    System.out.println("A: " + a.toBits() + ", " + a.toInt());
    System.out.println("B: " + b.toBits() + ", " + b.toInt());
    System.out.println("ALU: " + alu.toBits() + ", " + alu.toInt());

    final Transceiver transceiver = new Transceiver();
    transceiver.dir.setHigh();
    transceiver.enable.setLow();

    transceiver.aIn0.connectTo(alu.out0);
    transceiver.aIn1.connectTo(alu.out1);
    transceiver.aIn2.connectTo(alu.out2);
    transceiver.aIn3.connectTo(alu.out3);
    transceiver.aIn4.connectTo(alu.out4);
    transceiver.aIn5.connectTo(alu.out5);
    transceiver.aIn6.connectTo(alu.out6);
    transceiver.aIn7.connectTo(alu.out7);

    a.in0.connectTo(transceiver.aOut0);
    a.in1.connectTo(transceiver.aOut1);
    a.in2.connectTo(transceiver.aOut2);
    a.in3.connectTo(transceiver.aOut3);
    a.in4.connectTo(transceiver.aOut4);
    a.in5.connectTo(transceiver.aOut5);
    a.in6.connectTo(transceiver.aOut6);
    a.in7.connectTo(transceiver.aOut7);

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

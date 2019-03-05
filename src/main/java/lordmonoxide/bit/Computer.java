package lordmonoxide.bit;

import lordmonoxide.bit.components.ALU;
import lordmonoxide.bit.components.Clock;
import lordmonoxide.bit.components.Register;
import org.jetbrains.annotations.NotNull;

public class Computer {
  public static void main(@NotNull final String[] args) {
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

    System.out.println(alu);

    a.in0.setLow();
    a.in2.setLow();
    a.in4.setLow();
    a.in6.setLow();

    b.in1.setLow();
    b.in3.setLow();
    b.in5.setLow();
    b.in7.setLow();

    clock.out.setHigh();
    clock.out.setLow();

    System.out.println(alu);
  }
}

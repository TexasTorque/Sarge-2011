package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;

public class Drivebase extends Subsystem {

    private final Jaguar leftBack;
    private final Jaguar leftFront;
    private final Jaguar rightBack;
    private final Jaguar rightFront;
    private final Solenoid shifterSolenoid;
    private final Solenoid dropdriveSolenoid;

    private double leftSpeed;
    private double rightSpeed;
    private boolean highGear;
    private boolean dropCenter;

    public Drivebase() {
        leftBack = new Jaguar(0);
        leftFront = new Jaguar(0);
        rightBack = new Jaguar(0);
        rightFront = new Jaguar(0);

        shifterSolenoid = new Solenoid(0);
        dropdriveSolenoid = new Solenoid(0);
    }

    public void update() {
    }

    public void pushToDashboard() {
    }
}

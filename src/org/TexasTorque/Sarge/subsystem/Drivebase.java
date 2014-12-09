package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        leftSpeed = input.getLeftSpeed();
        rightSpeed = input.getRightSpeed();
        highGear = input.isHighGear();
        dropCenter = input.isDropCenter();

        if (outputEnabled) {
            leftBack.set(leftSpeed);
            leftFront.set(leftSpeed);
            rightBack.set(rightSpeed);
            rightFront.set(rightSpeed);
            shifterSolenoid.set(highGear);
            dropdriveSolenoid.set(dropCenter);
        }
    }

    public void pushToDashboard() {
        SmartDashboard.putNumber("leftSpeed", leftSpeed);
        SmartDashboard.putNumber("rightSpeed", rightSpeed);

        SmartDashboard.putBoolean("highGear", highGear);
        SmartDashboard.getBoolean("dropCenter", dropCenter);

    }
}

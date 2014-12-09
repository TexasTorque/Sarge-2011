package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.constants.Constants;

public class Drivebase extends Subsystem {

    private final Jaguar leftRear;
    private final Jaguar leftFront;
    private final Jaguar rightRear;
    private final Jaguar rightFront;
    private final DoubleSolenoid shifterSolenoid;
    private final Solenoid dropdriveSolenoid;

    private double leftSpeed;
    private double rightSpeed;
    private boolean highGear;
    private boolean dropCenter;

    public Drivebase() {
        leftRear = new Jaguar(Constants.REAR_LEFT_DRIVE);
        leftFront = new Jaguar(Constants.FRONT_LEFT_DRIVE);
        rightRear = new Jaguar(Constants.REAR_RIGHT_DRIVE);
        rightFront = new Jaguar(Constants.FRONT_RIGHT_DRIVE);

        shifterSolenoid = new DoubleSolenoid(Constants.SHIFTER_PORT_A, Constants.SHIFTER_PORT_B);
        dropdriveSolenoid = new Solenoid(Constants.DROPDRIVE_PORT);
    }

    public void update() {
        leftSpeed = input.getLeftSpeed();
        rightSpeed = input.getRightSpeed();
        highGear = input.isHighGear();
        dropCenter = input.isDropCenter();

        if (outputEnabled) {
            leftRear.set(leftSpeed);
            leftFront.set(leftSpeed);
            rightRear.set(rightSpeed);
            rightFront.set(rightSpeed);
            shifterSolenoid.set(highGear ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
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

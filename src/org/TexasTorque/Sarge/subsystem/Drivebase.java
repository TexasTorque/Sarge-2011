package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.constants.Ports;
import org.TexasTorque.Torquelib.component.Motor;

public class Drivebase extends Subsystem {

    private final Motor leftRear;
    private final Motor leftFront;
    private final Motor rightRear;
    private final Motor rightFront;
    private final DoubleSolenoid shifterSolenoid;
    private final Solenoid dropdriveSolenoid;

    private double leftSpeed;
    private double rightSpeed;
    private boolean highGear;
    private boolean dropCenter;

    public Drivebase() {
        leftRear = new Motor(new Jaguar(Ports.REAR_LEFT_DRIVE), false);
        leftFront = new Motor(new Jaguar(Ports.FRONT_LEFT_DRIVE), false);
        rightRear = new Motor(new Jaguar(Ports.REAR_RIGHT_DRIVE), true);
        rightFront = new Motor(new Jaguar(Ports.FRONT_RIGHT_DRIVE), true);

        shifterSolenoid = new DoubleSolenoid(Ports.SHIFTER_PORT_A, Ports.SHIFTER_PORT_B);
        dropdriveSolenoid = new Solenoid(Ports.DROPDRIVE_PORT);
    }

    public synchronized void update() {
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

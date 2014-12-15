package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.constants.Constants;
import org.TexasTorque.Sarge.constants.Ports;
import org.TexasTorque.Torquelib.component.Motor;
import org.TexasTorque.Torquelib.controlloop.TorquePID;

public class Arm extends Subsystem {

    //Motors
    private final Motor armMotor;
    private final Motor handMotor;
    private double targetAngle;
    private double previousTargetAngle;
    private double armAngle;
    private double armMotorSpeed;
    private double handMotorSpeed;
    //Pneumatics
    private DoubleSolenoid wristSolenoid;
    private Solenoid handSolenoid;
    private boolean wristDown;
    private boolean handOpen;
    //Angles
    
    //States
    public final static byte DOWN = 0;
    public final static byte INTAKE = 1;
    public final static byte OUTTAKE = 2;
    public final static byte CARRY = 3;
    public final static byte PLACE = 4;
    //Roller Powers
    private double intakePower = 1.0;
    private double outtakePower = -1.0;
    private double holdPower = 0.1;
    private double placePower = -0.25;
    private double offPower = 0.0;
    //PID
    private TorquePID armPID;
    double kFF;

    public Arm() {
        armMotor = new Motor(new Jaguar(Ports.ARM_MOTOR_PORT), false);
        handMotor = new Motor(new Jaguar(Ports.HAND_MOTOR_PORT), true);

        wristSolenoid = new DoubleSolenoid(Ports.WRIST_SOLENOID_A, Ports.WRIST_SOLENOID_B);
        handSolenoid = new Solenoid(Ports.HAND_SOLENOID);

        armPID = new TorquePID();
    }

    public void update() {
        armAngle = feedback.getArmAngle();
        targetAngle = input.getTargetAngle();

        if (input.getArmState() != state) {
            previousState = state;
            state = input.getArmState();
        }

        if (input.getTargetAngle() != targetAngle) {
            previousTargetAngle = targetAngle;
            targetAngle = input.getTargetAngle();
        }

        isOverride = input.isArmOverride();

        switch (state) {
            case DOWN:
                targetAngle = Constants.FLOOR_ANGLE.getDouble();

                handMotorSpeed = offPower;

                handOpen = false;
                wristDown = true;
                break;
            case INTAKE:
                targetAngle = Constants.FLOOR_ANGLE.getDouble();

                handMotorSpeed = intakePower;

                handOpen = false;
                wristDown = true;
                break;
            case OUTTAKE:
                if (targetAngle == Constants.RETRACT_ANGLE.getDouble()) {
                    targetAngle = Constants.FLOOR_ANGLE.getDouble();
                }

                handMotorSpeed = outtakePower;

                handOpen = true;
                wristDown = true;
                break;
            case CARRY:
                if (targetAngle == Constants.FLOOR_ANGLE.getDouble()) {
                    targetAngle = Constants.RETRACT_ANGLE.getDouble();
                }

                handMotorSpeed = holdPower;

                handOpen = false;
                wristDown = false;
                break;
            case PLACE:
                if (targetAngle > Constants.FLOOR_ANGLE.getDouble() || isOverride) {
                    handMotorSpeed = placePower;

                    handOpen = true;
                    wristDown = true;
                }
                break;
        }

        if (isOverride) {
            armMotorSpeed = input.getOverrideArmSpeed();
        } else {
            double pid = armPID.calculate(armAngle);
            double feedForward = Math.sin(targetAngle) * kFF;
            armPID.setSetpoint(targetAngle);
            armMotorSpeed = pid + feedForward;
        }

        if (outputEnabled) {
            wristSolenoid.set((wristDown) ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
            handSolenoid.set(handOpen);

            handMotor.set(handMotorSpeed);
            armMotor.set(armMotorSpeed);
        }
    }

    public void pushToDashboard() {
        SmartDashboard.putBoolean("WristDown", wristDown);
        SmartDashboard.putBoolean("HandOpen", handOpen);
        SmartDashboard.putNumber("HandRollerSpeed", handMotorSpeed);
        SmartDashboard.putNumber("ArmMotorSpeed", armMotorSpeed);
        SmartDashboard.putNumber("ArmState", state);
        SmartDashboard.putNumber("TargetAngle", targetAngle);
        SmartDashboard.putNumber("Angle", armAngle);
    }

    public void updateGains() {
        double kP = Constants.Arm_Kp.getDouble();
        double kI = Constants.Arm_Ki.getDouble();
        double kD = Constants.Arm_Kd.getDouble();
        kFF = Constants.Arm_Kff.getDouble();

        armPID.setPIDGains(kP, kI, kD);
    }
}

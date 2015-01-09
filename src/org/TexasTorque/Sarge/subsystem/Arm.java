package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.constants.Constants;
import org.TexasTorque.Sarge.constants.Ports;
import org.TexasTorque.Torquelib.component.Motor;
import org.TexasTorque.Torquelib.controlloop.FeedforwardPIV;
import org.TexasTorque.Torquelib.controlloop.TrapezoidalProfile;

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
    public final static byte NOTHING = 0;
    public final static byte INTAKE = 1;
    public final static byte OUTTAKE = 2;
    public final static byte CARRY = 3;
    public final static byte PLACE = 4;
    public final static byte DOWN = 5;
    //Roller Powers
    private double intakePower = 1.0;
    private double outtakePower = -1.0;
    private double holdPower = 0.1;
    private double placePower = -0.25;
    private double offPower = 0.0;
    //PID
    FeedforwardPIV armPIV;
    TrapezoidalProfile profile;
    double kFF;
    double velocity;

    public Arm() {
        armMotor = new Motor(new Jaguar(Ports.ARM_MOTOR_PORT), false);
        handMotor = new Motor(new Jaguar(Ports.HAND_MOTOR_PORT), true);

        wristSolenoid = new DoubleSolenoid(Ports.WRIST_SOLENOID_A, Ports.WRIST_SOLENOID_B);
        handSolenoid = new Solenoid(Ports.HAND_SOLENOID);

        armPIV = new FeedforwardPIV();
        profile = new TrapezoidalProfile(Constants.Arm_maxA.getDouble(), Constants.Arm_maxV.getDouble());
    }

    public void update() {
        armAngle = feedback.getArmAngle();
        velocity = feedback.getArmVelocity();
        targetAngle = input.getTargetAngle();

        byte newState;
            newState = input.getArmState();

        if (newState != state) {
            previousState = state;
            state = newState;
        }

        if (input.getTargetAngle() != targetAngle) {
            previousTargetAngle = targetAngle;
            targetAngle = input.getTargetAngle();
        }

        isOverride = input.isArmOverride();

        switch (state) {
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
            case DOWN:
                handOpen = false;
                wristDown = true;

                break;
            case NOTHING:
                handMotorSpeed = 0.0;
                break;
        }

        if (isOverride) {
            armMotorSpeed = input.getOverrideArmSpeed();
        } else {
            armPIV.setSetpoint(targetAngle);
            double error = targetAngle - armAngle;
            SmartDashboard.putNumber("error", error);
            profile.update(error, velocity, 0.0, 0.01);
            SmartDashboard.putNumber("curerntVelocity", profile.getVelocity());
            armMotorSpeed = armPIV.calculate(profile, armAngle , velocity, 0.01);
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
        SmartDashboard.putNumber("Velocity", velocity);
    }

    public void updateGains() {
        double kP = Constants.Arm_Kp.getDouble();
        double kI = Constants.Arm_Ki.getDouble();
        double kD = Constants.Arm_Kd.getDouble();
        kFF = Constants.Arm_Kff.getDouble();

        double kFFV = Constants.Arm_KffV.getDouble();
        double kFFA = Constants.Arm_KffA.getDouble();
        armPIV.setParams(kP, kI, kD, kFFV, kFFA);
        SmartDashboard.putNumber("kffv", kFFV);
        
        profile = new TrapezoidalProfile(Constants.Arm_maxA.getDouble(), Constants.Arm_maxV.getDouble());
    }
}

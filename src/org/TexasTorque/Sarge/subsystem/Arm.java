package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.constants.Constants;
import org.TexasTorque.Sarge.constants.Ports;
import org.TexasTorque.Torquelib.component.Motor;
import org.TexasTorque.Torquelib.controlloop.TorquePID;
import org.TexasTorque.Torquelib.controlloop.TorquePV;
import org.TexasTorque.Torquelib.controlloop.TorqueTMP;

public class Arm extends Subsystem {

    //Motors
    private final Motor armMotor;
    private final Motor handMotor;
    private double armMotorSpeed;
    private double handMotorSpeed;

    //Pneumatics
    private DoubleSolenoid wristSolenoid;
    private Solenoid handSolenoid;
    private boolean wristDown;
    private boolean handOpen;

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
    private TorqueTMP profile;
    private TorquePV armPV;
    private double targetAngle;
    private double armAngle;
    private double armVelocity;
    private double positionKff;

    public Arm() {
        armMotor = new Motor(new Victor(Ports.ARM_MOTOR_PORT), false);
        handMotor = new Motor(new Jaguar(Ports.HAND_MOTOR_PORT), true);

        wristSolenoid = new DoubleSolenoid(Ports.WRIST_SOLENOID_A, Ports.WRIST_SOLENOID_B);
        handSolenoid = new Solenoid(Ports.HAND_SOLENOID);

        profile = new TorqueTMP(Constants.Arm_maxV.getDouble(), Constants.Arm_maxA.getDouble());
        armPV = new TorquePV();
    }

    public void update() {
        armAngle = feedback.getArmAngle();
        armVelocity = feedback.getArmVelocity();
        targetAngle = input.getTargetAngle();
        isOverride = input.isArmOverride();

        state = input.getArmState();

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
            //Raw joystick value to control the arm motor.
            armMotorSpeed = input.getOverrideArmSpeed();
        } else {
            //Generate a new profile and figure out where we should be next.
            
            double angleError = targetAngle - feedback.getArmAngle();
            
            profile.generateTrapezoid(angleError, feedback.getArmVelocity());
            profile.calculateNextSituation(0.01);

            double pv = armPV.calculate(profile, angleError, armVelocity);
            
            //Calculate Feedforward and PID motor output. We need position feedforward
            //to get the arm neutrally buoyant in any position.
            double feedForward = positionKff * Math.cos(targetAngle);
            
            armMotorSpeed = pv + feedForward;
        }

        //Output to the robot
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
        SmartDashboard.putNumber("Velocity", armVelocity);
        SmartDashboard.putNumber("TargetVelocity", profile.getCurrentVelocity());
    }

    public void updateGains() {

        //PV gains
        double kP = Constants.Arm_Kp.getDouble();
        double kV = Constants.Arm_Kv.getDouble();

        //Feedforward Velocity and Acceleration gains
        double kFFV = Constants.Arm_KffV.getDouble();
        double kFFA = Constants.Arm_KffA.getDouble();

        armPV.setGains(kP, kV, kFFV, kFFA);
        
        //Position gains
        positionKff = Constants.Arm_positionFF.getDouble();

        //Reinitialize the TMP generator with new max V and A.
        profile = new TorqueTMP(Constants.Arm_maxV.getDouble(), Constants.Arm_maxA.getDouble());
    }
}

package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Torquelib.component.Motor;

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
    public final static double FLOOR_ANGLE = -50.0;
    public final static double LOW_ANGLE = -45.0;
    public final static double MIDDLE_ANGLE = 10.0;
    public final static double HIGH_ANGLE = 60.0;
    public final static double RETRACT_ANGLE = -60.0;
    
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

    public Arm() {
        armMotor = new Motor(new Jaguar(5), false);
        handMotor = new Motor(new Jaguar(3), true);

        wristSolenoid = new DoubleSolenoid(6, 5);
        handSolenoid = new Solenoid(1);
    }

    public void update() {
        armAngle = feedback.getArmAngle();
        targetAngle = input.getTargetAngle();
        
        if (input.getArmState() != state)
        {
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
                targetAngle = FLOOR_ANGLE;

                handMotorSpeed = offPower;

                handOpen = false;
                wristDown = true;
                break;
            case INTAKE:
                targetAngle = FLOOR_ANGLE;

                handMotorSpeed = intakePower;

                handOpen = false;
                wristDown = true;
                break;
            case OUTTAKE:
                if (targetAngle == RETRACT_ANGLE) {
                    targetAngle = FLOOR_ANGLE;
                }

                handMotorSpeed = outtakePower;

                handOpen = true;
                wristDown = true;
                break;
            case CARRY:
                if (targetAngle == FLOOR_ANGLE) {
                    targetAngle = RETRACT_ANGLE;
                }

                handMotorSpeed = holdPower;

                handOpen = false;
                wristDown = false;
                break;
            case PLACE:
                if (targetAngle > FLOOR_ANGLE || isOverride) {
                    handMotorSpeed = placePower;

                    handOpen = true;
                    wristDown = true;
                }
                break;
        }
        
        if (outputEnabled)
        {
            wristSolenoid.set((wristDown) ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
            handSolenoid.set(handOpen);
            
            handMotor.set(handMotorSpeed);
            
            if (isOverride)
            {
                armMotor.set(input.getOverrideArmSpeed());
            } else {
                //control loop output
            }
        }
    }

    public void pushToDashboard() {
        SmartDashboard.putBoolean("WristDown", wristDown);
        SmartDashboard.putBoolean("HandOpen", handOpen);
        SmartDashboard.putNumber("HandRollerSpeed", handMotorSpeed);
        SmartDashboard.putNumber("ArmMotorSpeed", armMotorSpeed);
    }
}

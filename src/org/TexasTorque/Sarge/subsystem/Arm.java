package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
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
    public final static double FLOOR_ANGLE = 0.0;
    public final static double LOW_ANGLE = 0.0;
    public final static double MIDDLE_ANGLE = 0.0;
    public final static double HIGH_ANGLE = 0.0;
    public final static double RETRACT_ANGLE = 0.0;
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
        handMotor = new Motor(new Jaguar(3), false);

        wristSolenoid = new DoubleSolenoid(5, 6);
        handSolenoid = new Solenoid(1);
    }

    public void update() {
        armAngle = feedback.getArmAngle();
        targetAngle = input.getTargetAngle();
        
        state = input.getArmState();
        
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
                if (previousTargetAngle == RETRACT_ANGLE) {
                    targetAngle = FLOOR_ANGLE;
                }

                handMotorSpeed = outtakePower;

                handOpen = true;
                wristDown = true;
                break;
            case CARRY:
                if (previousTargetAngle == FLOOR_ANGLE) {
                    targetAngle = RETRACT_ANGLE;
                }

                handMotorSpeed = holdPower;

                handOpen = false;
                wristDown = false;
                break;
            case PLACE:
                if (targetAngle > FLOOR_ANGLE || previousState == CARRY) {
                    handMotorSpeed = placePower;

                    handOpen = true;
                    wristDown = true;
                }
                break;
        }

        previousTargetAngle = targetAngle;
        previousState = state;
    }

    public void pushToDashboard() {
    }
}

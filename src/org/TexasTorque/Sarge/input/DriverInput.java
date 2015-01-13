package org.TexasTorque.Sarge.input;

import edu.wpi.first.wpilibj.Joystick;
import org.TexasTorque.Sarge.constants.Constants;
import org.TexasTorque.Sarge.subsystem.Arm;
import org.TexasTorque.Torquelib.component.GenericController;
import org.TexasTorque.Torquelib.util.TorqueToggle;

public class DriverInput extends InputSystem {

    private final GenericController driver;
    private final GenericController operator;
    private final TorqueToggle shifterToggle;
    private final TorqueToggle dropCenterToggle;
    
    public DriverInput() {
        driver = new GenericController(1, false, 0.2);
        operator = new GenericController(2, false, 0.2);

        shifterToggle = new TorqueToggle();
        dropCenterToggle = new TorqueToggle();
        
        armState = Arm.CARRY;
        targetAngle = Constants.FLOOR_ANGLE.getDouble();
    }

    public void run() {
        //Drivebase
        double y = -driver.getLeftYAxis();
        double x = driver.getRightXAxis();

        leftSpeed = y + x;
        rightSpeed = y - x;

        shifterToggle.calc(driver.getRightBumper());
        dropCenterToggle.calc(driver.getAButton());

        isLowGear = shifterToggle.get();
        isDropCenter = dropCenterToggle.get();

        //Arm
        if (operator.getYButton()) {
            targetAngle = Constants.HIGH_ANGLE.getDouble();
            armState = Arm.CARRY;
        } else if (operator.getXButton()) {
            targetAngle = Constants.MIDDLE_ANGLE.getDouble();
            armState = Arm.CARRY;
        } else if (operator.getAButton()) {
            targetAngle = Constants.RETRACT_ANGLE.getDouble();
            armState = Arm.CARRY;
        }

        if (operator.getRightBumper()) {
            targetAngle = Constants.FLOOR_ANGLE.getDouble();
            armState = Arm.INTAKE;
        } else if (operator.getBButton()) {
            armState = Arm.CARRY;
        } else if (operator.getLeftBumper()) {
            armState = Arm.OUTTAKE;
        } else if (operator.getRightTrigger()) {
            armState = Arm.PLACE;
        } else {
            armState = Arm.NOTHING;
        }
        
        if (operator.getLeftCenterButton()) {
            armOverride = true;
        } else if (operator.getRightCenterButton()) {
            armOverride = false;
        }
        
        overrideArmSpeed = operator.getY(Joystick.Hand.kRight);
        
        if (operator.getLeftStickClick()) {
            feedback.resetArmAngle();
        }
    }
}

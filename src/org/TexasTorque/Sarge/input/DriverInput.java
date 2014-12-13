package org.TexasTorque.Sarge.input;

import org.TexasTorque.Sarge.subsystem.Arm;
import org.TexasTorque.Torquelib.component.GenericController;
import org.TexasTorque.Torquelib.util.TorqueToggle;

public class DriverInput extends InputSystem {

    private final GenericController driver;
    private final GenericController operator;
    private final TorqueToggle shifterToggle;
    private final TorqueToggle dropCenterToggle;
    private boolean wasIntaking;

    public DriverInput() {
        driver = new GenericController(1, false, 0.2);
        operator = new GenericController(2, false, 0.2);

        shifterToggle = new TorqueToggle();
        dropCenterToggle = new TorqueToggle();
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
            targetAngle = Arm.HIGH_ANGLE;
        } else if (operator.getXButton()) {
            targetAngle = Arm.MIDDLE_ANGLE;
        } else if (operator.getAButton()) {
            targetAngle = Arm.RETRACT_ANGLE;
        }
        
        if (operator.getRightBumper() && !wasIntaking) {
            targetAngle = Arm.FLOOR_ANGLE;
            armState = Arm.INTAKE;

            wasIntaking = true;
        } else if (!operator.getRightBumper() && wasIntaking) {
            targetAngle = Arm.FLOOR_ANGLE;
            armState = Arm.DOWN;

            wasIntaking = false;
        } else if (operator.getBButton()) {
            armState = Arm.CARRY;
        } else if (operator.getLeftBumper()) {
            armState = Arm.OUTTAKE;
        } else if (operator.getRightTrigger()) {
            armState = Arm.PLACE;
        }
        
        if (operator.getLeftCenterButton())
        {
            armOverride = true;
        } else if (operator.getRightCenterButton()) {
            armOverride = false;
        }
    }
}

package org.TexasTorque.Sarge.input;

import edu.wpi.first.wpilibj.Joystick;

public class DriverInput extends InputSystem {

    private final Joystick driver;

    public DriverInput() {
        driver = new Joystick(1);
    }

    public void run() {
        //Drivebase
        double x = -driver.getRawAxis(2);
        double y = driver.getRawAxis(5);
        leftSpeed = y + x;
        rightSpeed = y - x;
        isDropCenter = driver.getRawButton(5);
        isHighGear = driver.getRawButton(6);
    }
}

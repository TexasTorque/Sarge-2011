package org.TexasTorque.Sarge.input;

import edu.wpi.first.wpilibj.Joystick;

public class DriverInput extends InputSystem {

    private final Joystick driver;

    public DriverInput() {
        driver = new Joystick(1);
    }

    public void run() {
        double x = driver.getRawAxis(4);
        double y = driver.getRawAxis(2);
        leftSpeed = y + x;
        rightSpeed = y - x;
    }
}
package org.TexasTorque.Sarge.input;

public abstract class InputSystem {

    //Drivebase
    protected volatile double leftSpeed;
    protected volatile double rightSpeed;
    protected volatile boolean isHighGear;
    protected volatile boolean isDropCenter;

    public synchronized double getLeftSpeed() {
        return leftSpeed;
    }

    public synchronized double getRightSpeed() {
        return rightSpeed;
    }

    public synchronized boolean isHighGear() {
        return isHighGear;
    }

    public synchronized boolean isDropCenter() {
        return isDropCenter;
    }

}

package org.TexasTorque.Sarge.feedback;

public abstract class FeedbackSystem implements Runnable {

    //drivebase
    protected double leftPosition;
    protected double rightPosition;
    protected double leftVelocity;
    protected double rightVelocity;

    public double getLeftPosition() {
        return leftPosition;
    }

    public double getRightPosition() {
        return rightPosition;
    }

    public double getLeftVelocity() {
        return leftVelocity;
    }

    public double getRightVelocity() {
        return rightVelocity;
    }

    //arm
    protected double armAngle;

    public double getArmAngle() {
        return armAngle;
    }
}

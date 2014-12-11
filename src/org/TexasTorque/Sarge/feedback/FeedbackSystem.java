package org.TexasTorque.Sarge.feedback;

public abstract class FeedbackSystem implements Runnable {

    //drivebase
    private double leftPosition;
    private double rightPosition;
    private double leftVelocity;
    private double rightVelocity;

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
    private double armAngle;

    public double getArmAngle() {
        return armAngle;
    }
}

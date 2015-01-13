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
    protected double armVelocity;

    public double getArmAngle() {
        return armAngle;
    }
    
    public double getArmVelocity() {
        return armVelocity;
    }
    
    public abstract void resetArmAngle();
    
    public abstract void loadParams();
}

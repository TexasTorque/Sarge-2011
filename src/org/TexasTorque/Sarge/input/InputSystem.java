package org.TexasTorque.Sarge.input;

import org.TexasTorque.Sarge.feedback.FeedbackSystem;

public abstract class InputSystem implements Runnable {
    
    FeedbackSystem feedback;
    
    public void setFeedBackSystem(FeedbackSystem feed) {
        feedback = feed;
    }

    //Drivebase
    protected volatile double leftSpeed;
    protected volatile double rightSpeed;
    protected volatile boolean isLowGear;
    protected volatile boolean isDropCenter;

    public synchronized double getLeftSpeed() {
        return leftSpeed;
    }

    public synchronized double getRightSpeed() {
        return rightSpeed;
    }

    public synchronized boolean isHighGear() {
        return isLowGear;
    }

    public synchronized boolean isDropCenter() {
        return isDropCenter;
    }
    //Arm
    protected volatile byte armState;
    protected volatile double targetAngle;
    protected volatile boolean armOverride;
    protected volatile double overrideArmSpeed;

    public synchronized byte getArmState() {
        return armState;
    }

    public synchronized double getTargetAngle() {
        return targetAngle;
    }

    public synchronized boolean isArmOverride() {
        return armOverride;
    }

    public synchronized double getOverrideArmSpeed() {
        return overrideArmSpeed;
    }
}

package org.TexasTorque.Sarge.subsystem;

import org.TexasTorque.Sarge.feedback.FeedbackSystem;
import org.TexasTorque.Sarge.input.InputSystem;

public abstract class Subsystem {

    protected InputSystem input;
    protected FeedbackSystem feedback;

    protected boolean outputEnabled;
    protected volatile int state;

    public void enableOutput(boolean enable) {
        outputEnabled = enable;
    }

    public void setInputSystem(InputSystem in) {
        input = in;
    }

    public void setFeedbackSystem(FeedbackSystem feed) {
        feedback = feed;
    }

    public abstract void update();

    public abstract void pushToDashboard();

    public void updateGains() {

    }
}
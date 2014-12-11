package org.TexasTorque.Sarge;

import org.TexasTorque.Sarge.feedback.FeedbackSystem;
import org.TexasTorque.Sarge.feedback.SensorFeedback;
import org.TexasTorque.Sarge.input.DriverInput;
import org.TexasTorque.Sarge.input.InputSystem;
import org.TexasTorque.Sarge.subsystem.Drivebase;

public class Robot extends TorqueIterative {

    InputSystem input;
    DriverInput driverInput;
    
    FeedbackSystem feedback;
    SensorFeedback sensorFeedback;
    
    Drivebase drivebase;
    
    public void robotInit() {
        driverInput = new DriverInput();
        
        sensorFeedback = new SensorFeedback();
        
        drivebase = new Drivebase();
    }

    public void teleopInit() {
        input = driverInput;
        
        feedback = sensorFeedback;
        
        drivebase.setInputSystem(input);
        drivebase.enableOutput(true);
    }

    public void teleopPeriodic() {
        input.run();
        
        feedback.run();
        
        drivebase.update();
        drivebase.pushToDashboard();
    }
    
    
    
    
    
}

package org.TexasTorque.Sarge;

import edu.wpi.first.wpilibj.Compressor;
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
    Compressor compressor;
    
    public void robotInit() {
        driverInput = new DriverInput();
        
        sensorFeedback = new SensorFeedback();
        
        drivebase = new Drivebase();
        compressor = new Compressor(13, 1);
        compressor.start();
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

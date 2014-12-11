package org.TexasTorque.Sarge.feedback;

import org.TexasTorque.Torquelib.component.TorquePotentiometer;
import org.TexasTorque.Torquelib.component.TorqueQuadrature;

public class SensorFeedback extends FeedbackSystem {
    
    //Drivebase
    private TorqueQuadrature leftDriveEncoder;
    private TorqueQuadrature rightDriveEncoder;
    
    //Arm
    private TorquePotentiometer armPotentiometer;
    
    public SensorFeedback()
    {
        
    }
    
    public void run()
    {
        
    }
}

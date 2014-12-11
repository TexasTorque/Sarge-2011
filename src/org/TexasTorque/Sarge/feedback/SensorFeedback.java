package org.TexasTorque.Sarge.feedback;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        leftDriveEncoder = new TorqueQuadrature(4, 5, false);
        rightDriveEncoder = new TorqueQuadrature(2, 3, true);
        
        armPotentiometer = new TorquePotentiometer(1);
    }
    
    public void run()
    {
        SmartDashboard.putNumber("armvoltage", armPotentiometer.getRaw());
        
        leftPosition = leftDriveEncoder.get();
        leftVelocity = leftDriveEncoder.getInstantRate();
        
        rightPosition = rightDriveEncoder.get();
        rightVelocity = rightDriveEncoder.getInstantRate();
    }
}

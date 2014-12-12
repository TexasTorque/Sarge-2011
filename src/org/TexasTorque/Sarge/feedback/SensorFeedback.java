package org.TexasTorque.Sarge.feedback;

import org.TexasTorque.Torquelib.component.TorquePotentiometer;
import org.TexasTorque.Torquelib.component.TorqueQuadrature;

public class SensorFeedback extends FeedbackSystem {
    
    //Drivebase
    private TorqueQuadrature leftDriveEncoder;
    private TorqueQuadrature rightDriveEncoder;
    
    //Arm
    private TorquePotentiometer armPotentiometer;
    private double horizontalVoltage = 0.0;
    private double upVoltage = 0.0;
    private double horizontalAngle = 90.0;
    private double upAngle = 0.0;
    
    public SensorFeedback()
    {
        leftDriveEncoder = new TorqueQuadrature(4, 5, false);
        rightDriveEncoder = new TorqueQuadrature(2, 3, true);
        
        armPotentiometer = new TorquePotentiometer(1);
        armPotentiometer.setRange(horizontalVoltage, upVoltage);
    }
    
    public void run()
    {
        //DriveBase
        leftPosition = leftDriveEncoder.get();
        leftVelocity = leftDriveEncoder.getInstantRate();
        
        rightPosition = rightDriveEncoder.get();
        rightVelocity = rightDriveEncoder.getInstantRate();
        
        //ARm
        armAngle = armPotentiometer.get() * (upAngle - horizontalAngle) + horizontalAngle;
    }
}

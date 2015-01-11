package org.TexasTorque.Sarge.feedback;

import edu.wpi.first.wpilibj.CounterBase;
import org.TexasTorque.Sarge.constants.Constants;
import org.TexasTorque.Sarge.constants.Ports;
import org.TexasTorque.Torquelib.component.TorqueQuadrature;

public class SensorFeedback extends FeedbackSystem {

    //Drivebase
    private TorqueQuadrature leftDriveEncoder;
    private TorqueQuadrature rightDriveEncoder;

    //Arm
    private TorqueQuadrature armEncoder;
    private double bottomAngle;
    private double degreesPerClick;
    
    public SensorFeedback() {
        leftDriveEncoder = new TorqueQuadrature(4, 5, false);
        rightDriveEncoder = new TorqueQuadrature(2, 3, true);
        
        bottomAngle = Constants.Arm_BottomAngle.getDouble();
        degreesPerClick = Constants.Arm_DegreesPerClick.getDouble();
        
        armEncoder = new TorqueQuadrature(Ports.ARM_ENCODER_A, Ports.ARM_ENCODER_B, false, CounterBase.EncodingType.k4X);
    }

    public void run() {
        //DriveBase
        leftPosition = leftDriveEncoder.get();
        leftVelocity = leftDriveEncoder.getInstantRate();

        rightPosition = rightDriveEncoder.get();
        rightVelocity = rightDriveEncoder.getInstantRate();

        //Arm
        armEncoder.calc();
        
        armAngle = armEncoder.get() * degreesPerClick + bottomAngle;
        armVelocity = armEncoder.getSecantRate();
    }

    public void loadParams() {
        bottomAngle = Constants.Arm_BottomAngle.getDouble();
        degreesPerClick = Constants.Arm_DegreesPerClick.getDouble();
    }
}

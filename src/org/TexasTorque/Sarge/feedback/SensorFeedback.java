package org.TexasTorque.Sarge.feedback;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.constants.Ports;
import org.TexasTorque.Torquelib.component.TorqueEncoder;

public class SensorFeedback extends FeedbackSystem {

    //Drivebase
    private TorqueEncoder leftDriveEncoder;
    private TorqueEncoder rightDriveEncoder;

    //Arm
    private TorqueEncoder armEncoder;
    private double bottomAngle = -66;
    private double degreesPerClick = 1.44;
    
    public SensorFeedback() {
        leftDriveEncoder = new TorqueEncoder(4, 5, false, CounterBase.EncodingType.k2X);
        leftDriveEncoder.start();
        rightDriveEncoder = new TorqueEncoder(2, 3, true, CounterBase.EncodingType.k2X);
        rightDriveEncoder.start();
        
        armEncoder = new TorqueEncoder(Ports.ARM_ENCODER_A, Ports.ARM_ENCODER_B, false, CounterBase.EncodingType.k2X);
        armEncoder.start();
    }

    public void run() {
        //DriveBase
        leftDriveEncoder.calc();
        leftPosition = leftDriveEncoder.get();
        leftVelocity = leftDriveEncoder.getRate();

        rightDriveEncoder.calc();
        rightPosition = rightDriveEncoder.get();
        rightVelocity = rightDriveEncoder.getRate();

        //Arm
        armEncoder.calc();
        
        armAngle = armEncoder.getDistance() * degreesPerClick + bottomAngle;
        armVelocity = armEncoder.getRate();
    }

    public void resetArmAngle() {
        armEncoder.stop();
        armEncoder.reset();
        armEncoder.start();
    }
    
    public void loadParams() {
    }
}

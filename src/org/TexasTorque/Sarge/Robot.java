package org.TexasTorque.Sarge;

import org.TexasTorque.Sarge.input.DriverInput;
import org.TexasTorque.Sarge.input.InputSystem;
import org.TexasTorque.Sarge.subsystem.Drivebase;

public class Robot extends TorqueIterative {

    InputSystem input;
    DriverInput driverInput;
    Drivebase drivebase;
    
    public void robotInit() {
        driverInput = new DriverInput();
        drivebase = new Drivebase();
    }

    public void teleopInit() {
        input = driverInput;
        
        drivebase.setInputSystem(input);
        drivebase.enableOutput(true);
    }

    public void teleopPeriodic() {
        input.run();
        drivebase.update();
        drivebase.pushToDashboard();
    }
    
    
    
    
    
}

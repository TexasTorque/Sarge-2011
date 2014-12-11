package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import org.TexasTorque.Torquelib.component.Motor;

public class Arm extends Subsystem {

    private final Motor armMotor;
    private final Motor handMotor;
    
    private DoubleSolenoid wristSolenoid;
    private Solenoid handSolenoid;
    
    private double targetAngle;
    private double armAngle;

    public Arm() {
        armMotor = new Motor(new Jaguar(5), false);
        handMotor = new Motor(new Jaguar(3), false);
        
        wristSolenoid = new DoubleSolenoid(5, 6);
        handSolenoid = new Solenoid(1);
    }

    public void update() {
    }

    public void pushToDashboard() {
    }
}

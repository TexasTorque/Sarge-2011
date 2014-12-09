package org.TexasTorque.Sarge.subsystem;

import edu.wpi.first.wpilibj.Jaguar;

public class Arm extends Subsystem {

    private final Jaguar armMotor;
    private final Jaguar handMotor;

    public Arm() {
        armMotor = new Jaguar(0);
        handMotor = new Jaguar(0);
    }

    public void update() {
    }

    public void pushToDashboard() {
    }
}

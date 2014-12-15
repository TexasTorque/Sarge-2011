package org.TexasTorque.Sarge.constants;

import org.TexasTorque.Torquelib.util.Parameters.Constant;

public class Constants {
    
    //Arm PID
    public static final Constant Arm_Kp = new Constant("R_ArmKp", 0.0);
    public static final Constant Arm_Ki = new Constant("R_ArmKi", 0.0);
    public static final Constant Arm_Kd = new Constant("R_ArmKd", 0.0);
    public static final Constant Arm_Kff = new Constant("R_ArmKff", 0.0);
    
    //Arm Setpoints
    public final static Constant FLOOR_ANGLE = new Constant("R_FloorAngle", -50.0);
    public final static Constant LOW_ANGLE = new Constant("R_LowAngle", -45.0);
    public final static Constant MIDDLE_ANGLE = new Constant("R_MiddleAngle", 10.0);
    public final static Constant HIGH_ANGLE = new Constant("R_HighAngle", 55.0);
    public final static Constant RETRACT_ANGLE = new Constant("R_RetractAngle", 55.0);
    
}

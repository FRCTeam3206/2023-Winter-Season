package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
public class Arm extends SubsystemBase{
    CANSparkMax armMotor=new CANSparkMax(Ports.ARM_MOTOR, MotorType.kBrushless);
    public Arm(){
        SparkMaxPIDController pid=armMotor.getPIDController();
        pid.setP(0);
        pid.setD(0);
        pid.setI(0);
        pid.setFF(0);
    }
    public void setPos(int pos){
        armMotor.getPIDController().setReference(pos, com.revrobotics.CANSparkMax.ControlType.kPosition);
    }
    
}

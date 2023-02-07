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
    CANSparkMax elbow=new CANSparkMax(Ports.ARM_MOTOR, MotorType.kBrushless);
    CANSparkMax telescope = new CANSparkMax(Ports.ARM_MOTOR, MotorType.kBrushless);
    public Arm(){
        SparkMaxPIDController elbowPid= elbow.getPIDController();
        elbowPid.setP(0);
        elbowPid.setD(0);
        elbowPid.setI(0);
        elbowPid.setFF(0);
        SparkMaxPIDController telePid = telescope.getPIDController();
        telePid.setP(0);
        telePid.setD(0);
        telePid.setI(0);
        telePid.setFF(0);
    }
    public void setElbowPos(int pos){
        elbow.getPIDController().setReference(pos, com.revrobotics.CANSparkMax.ControlType.kPosition);
    }
    public void setTelePos(int pos){
        telescope.getPIDController().setReference(pos, com.revrobotics.CANSparkMax.ControlType.kPosition);
    }
    
}

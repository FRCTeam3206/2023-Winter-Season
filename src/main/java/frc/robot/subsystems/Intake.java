package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
public class Intake extends SubsystemBase{
    VictorSPX intakeMotor=new VictorSPX(Ports.INTAKE_MOTOR);
    public Intake(){}
    public void runIntake(){
        intakeMotor.set(ControlMode.PercentOutput, 1);
    }
    public void stopIntake(){
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
}

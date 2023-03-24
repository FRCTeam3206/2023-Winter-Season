package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    CANSparkMax elbow = new CANSparkMax(CANIDs.ARM_ELBOW, MotorType.kBrushless);
    VictorSPX telescope = new VictorSPX(CANIDs.ARM_TELE);
    Encoder elbowEncoder = new Encoder(Encoders.ELBOW_A, Encoders.ELBOW_B, false);
    PIDController elbowPID = new PIDController(ArmPID.elbowKp, ArmPID.elbowKi, ArmPID.elbowKd);
    AnalogPotentiometer teleEncoder = new AnalogPotentiometer(0, ArmPID.ARM_SMALL, ArmPID.ARM_BIG);
    PIDController telePID = new PIDController(ArmPID.teleKp, ArmPID.teleKi, ArmPID.teleKd);

    public Arm() {
        elbowEncoder.setDistancePerPulse(360 / 1024);
    }

    public void setElbowPos(double pos) {
        elbowPID.setSetpoint(pos);
    }

    public void setTelePos(int pos) {
        telePID.setSetpoint(pos);
    }

    public void periodic() {
        elbow.set(elbowPID.calculate(elbowEncoder.getDistance()));
        telescope.set(VictorSPXControlMode.PercentOutput, telePID.calculate(teleEncoder.get()));
    }

}

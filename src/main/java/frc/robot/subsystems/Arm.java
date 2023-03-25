package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    CANSparkMax elbow = new CANSparkMax(CANIDs.ARM_ELBOW, MotorType.kBrushless);
    VictorSPX telescope = new VictorSPX(CANIDs.ARM_TELE);
    DutyCycleEncoder elbowEncoder = new DutyCycleEncoder(Encoders.ELBOW_A);
    PIDController elbowPID = new PIDController(ArmConstants.elbowKp, ArmConstants.elbowKi, ArmConstants.elbowKd);
    AnalogPotentiometer teleEncoder = new AnalogPotentiometer(0, ArmConstants.ARM_SMALL, ArmConstants.ARM_BIG);
    PIDController telePID = new PIDController(ArmConstants.teleKp, ArmConstants.teleKi, ArmConstants.teleKd);

    public Arm() {
        elbowEncoder.setDistancePerRotation(2 * Math.PI);
        elbowPID.setSetpoint(ArmConstants.ARM_INITAL_ANGLE);
        telePID.setSetpoint(ArmConstants.ARM_SMALL);
    }

    public void setElbowPos(double pos) {
        elbowPID.setSetpoint(pos);
    }

    public void setTelePos(int pos) {
        telePID.setSetpoint(pos);
    }

    public void periodic() {
        // elbow.set(elbowPID.calculate(elbowEncoder.getDistance() +
        // ArmConstants.ARM_INITAL_ANGLE));
        // telescope.set(VictorSPXControlMode.PercentOutput,
        // telePID.calculate(teleEncoder.get() + ArmConstants.ARM_SMALL));
        SmartDashboard.putNumber("Elbow Position", elbowEncoder.getDistance());
        SmartDashboard.putNumber("Tele Position", teleEncoder.get());
    }

}

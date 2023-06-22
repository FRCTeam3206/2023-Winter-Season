package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    CANSparkMax elbow = new CANSparkMax(CANIDs.ARM_ELBOW, MotorType.kBrushless);
    DutyCycleEncoder elbowEncoder = new DutyCycleEncoder(Encoders.ELBOW_A);
    // AnalogPotentiometer teleEncoder = new AnalogPotentiometer(0,
    // ArmConstants.ARM_SMALL, ArmConstants.ARM_BIG);
    // PIDController telePID = new PIDController(ArmConstants.teleKp,
    // ArmConstants.teleKi, ArmConstants.teleKd);
    PIDController elbowPID = new PIDController(.015, 0, 0);
    public double position = 0;

    public Arm() {
        // telePID.setSetpoint(ArmConstants.ARM_SMALL);
        elbow.setIdleMode(IdleMode.kBrake);
        elbowEncoder.setPositionOffset(.206);
        elbow.setSmartCurrentLimit(10);
    }

    public void setArmPower(double power) {
        // elbowPID.setSetpoint(Constants.ArmConstants.ARM_ANGLE_UP);
        elbow.set(power);
    }

    public void setArmPosition(double position) {
        SmartDashboard.putNumber("Desired Arm Pos", position);
        this.position = position;
        setAbsoluteArmPosition(position);
    }

    public void setAbsoluteArmPosition(double position) {
        elbowPID.setSetpoint(position);
    }

    public void periodic() {
        // elbow.set(elbowPID.calculate(elbowEncoder.getDistance() +
        // ArmConstants.ARM_INITAL_ANGLE));
        // telescope.set(VictorSPXControlMode.PercentOutput,
        // telePID.calculate(teleEncoder.get() + ArmConstants.ARM_SMALL));
        elbow.set(elbowPID.calculate(-elbowEncoder.getDistance() * 360));

        SmartDashboard.putData(elbowEncoder);
        SmartDashboard.putNumber("Arm Angle", -elbowEncoder.getDistance() * 360);
    }

}

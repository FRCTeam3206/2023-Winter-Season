package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    CANSparkMax elbow = new CANSparkMax(CANIDs.ARM_ELBOW, MotorType.kBrushless);
    DutyCycleEncoder elbowEncoder = new DutyCycleEncoder(Encoders.ELBOW_A);
    ArmFeedforward aff = new ArmFeedforward(0., ArmConstants.kG, ArmConstants.kV);
    // AnalogPotentiometer teleEncoder = new AnalogPotentiometer(0,
    // ArmConstants.ARM_SMALL, ArmConstants.ARM_BIG);
    // PIDController telePID = new PIDController(ArmConstants.teleKp,
    // ArmConstants.teleKi, ArmConstants.teleKd);
    PIDController elbowPID = new PIDController(.16, 0, 0);
    public double position = 0;

    public Arm() {
        // telePID.setSetpoint(ArmConstants.ARM_SMALL);
        elbow.setIdleMode(IdleMode.kCoast);
        elbowEncoder.setPositionOffset(.206);
        elbow.setSmartCurrentLimit(7);
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
        elbow.setVoltage(aff.calculate(-(position * Math.PI / 180 - Math.PI / 2), 0) +
                elbowPID.calculate(-elbowEncoder.getDistance() * 360));
        SmartDashboard.putNumber("Arm Volage", aff.calculate(-(position * Math.PI / 180 - Math.PI / 2), 0));
        SmartDashboard.putData(elbowEncoder);
        SmartDashboard.putNumber("Arm Angle", -elbowEncoder.getDistance() * 360);
    }

}

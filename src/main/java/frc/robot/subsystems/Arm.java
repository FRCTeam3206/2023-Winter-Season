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
import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    CANSparkMax elbow = new CANSparkMax(CANIDs.ARM_ELBOW, MotorType.kBrushless);
    // AnalogPotentiometer teleEncoder = new AnalogPotentiometer(0,
    // ArmConstants.ARM_SMALL, ArmConstants.ARM_BIG);
    // PIDController telePID = new PIDController(ArmConstants.teleKp,
    // ArmConstants.teleKi, ArmConstants.teleKd);

    public Arm() {
        // telePID.setSetpoint(ArmConstants.ARM_SMALL);
    }

    public void setArmPower(double power) {
        // elbowPID.setSetpoint(Constants.ArmConstants.ARM_ANGLE_UP);
        elbow.set(power);
    }

    public void periodic() {
        // elbow.set(elbowPID.calculate(elbowEncoder.getDistance() +
        // ArmConstants.ARM_INITAL_ANGLE));
        // telescope.set(VictorSPXControlMode.PercentOutput,
        // telePID.calculate(teleEncoder.get() + ArmConstants.ARM_SMALL));
        // SmartDashboard.putData("Tele Position", teleEncoder);
    }

}

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    CANSparkMax elbow = new CANSparkMax(Ports.ARM_MOTOR, MotorType.kBrushless);
    VictorSPX telescope = new VictorSPX(Ports.ARM_MOTOR);
    Encoder elbowEncoder = new Encoder(0, 1, false);
    PIDController elbowPID = new PIDController(1 / 180., 0, 0);
    AnalogPotentiometer teleEncoder = new AnalogPotentiometer(0, 37, 52);
    PIDController telePID = new PIDController(1 / 26., 0, 0);

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

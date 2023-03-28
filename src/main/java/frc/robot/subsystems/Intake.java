package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    CANSparkMax intakeMotor = new CANSparkMax(Ports.INTAKE_MOTOR, MotorType.kBrushless);
    Encoder encoder = new Encoder(Encoders.TRANSPORT_A, Encoders.TRANSPORT_B);
    VictorSPX transportMotor = new VictorSPX(CANIDs.TRANSPORT);
    boolean intakeUp = true;
    boolean movingTransport = false;
    boolean transportUp = false;
    Solenoid deploy = new Solenoid(PneumaticsModuleType.REVPH, Ports.INTAKE_DEPLOY);

    public Intake() {
        encoder.setDistancePerPulse(Math.PI / 2048.);
        encoder.setReverseDirection(true);
    }

    public void runIntake(double speed) {
        // deploy.set(true);
        intakeMotor.set(-speed);
    }

    public void setDeploy(boolean down) {
        deploy.set(down);
        intakeUp = !down;
    }

    public void setTransport(boolean down) {
        transportUp = !down;
        movingTransport = true;
    }

    public void overrideTransport(double downSpeed) {
        transportMotor.set(VictorSPXControlMode.PercentOutput, -downSpeed);
    }

    public void periodic() {
        SmartDashboard.putData("Drawbridge Location", encoder);
        SmartDashboard.putBoolean("Transport Location", transportUp);
        if (movingTransport) {
            // deploy.set(true);
            if (transportUp && encoder.getDistance() < TRANSPORT_ENCODER_END_POS) {
                transportMotor.set(VictorSPXControlMode.PercentOutput, -1);
            } else if (!transportUp && encoder.getDistance() > 0) {
                transportMotor.set(VictorSPXControlMode.PercentOutput, 1);
            } else {
                transportMotor.set(VictorSPXControlMode.PercentOutput, 0);
                movingTransport = false;
            }
        }
    }
}

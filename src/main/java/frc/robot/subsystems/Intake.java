package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    CANSparkMax intakeMotor = new CANSparkMax(Ports.INTAKE_MOTOR, MotorType.kBrushless);
    Encoder encoder = new Encoder(Encoders.TRANSPORT_A, Encoders.TRANSPORT_B);
    VictorSPX transportMotor = new VictorSPX(CANIDs.TRANSPORT);
    boolean intakeUp = true;
    boolean movingTransport = false;
    boolean transportUp = false;
    Solenoid deploy = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.INTAKE_DEPLOY);

    public Intake() {
    }

    public void runIntake(double speed) {
        // deploy.set(true);
        intakeMotor.set(-speed);
    }

    public void setIntake(boolean down) {
        deploy.set(down);
        intakeUp = !down;
    }

    public void setTransport(boolean down) {
        transportUp = !down;
        movingTransport = true;
    }

    public void periodic() {
        if (movingTransport) {
            deploy.set(true);
            if (transportUp && encoder.get() < TRANSPORT_ENCODER_END_POS) {
                transportMotor.set(VictorSPXControlMode.PercentOutput, -.5);
            } else if (!transportUp && encoder.get() > 0) {
                transportMotor.set(VictorSPXControlMode.PercentOutput, .5);
            } else {
                transportMotor.set(VictorSPXControlMode.PercentOutput, 0);
                movingTransport = false;
            }
        }
    }
}

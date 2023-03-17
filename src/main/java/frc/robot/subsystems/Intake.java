package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    CANSparkMax intakeMotor = new CANSparkMax(Ports.INTAKE_MOTOR, MotorType.kBrushless);
    WPI_VictorSPX seeSawMotor = new WPI_VictorSPX(1);

    // Solenoid deploy = new Solenoid(PneumaticsModuleType.CTREPCM,
    // Ports.INTAKE_DEPLOY);

    public void runIntake() {
        // deploy.set(true);
        intakeMotor.set(.7);
    }

    public void stopIntake() {
        // deploy.set(false);
        intakeMotor.set(0);
    }

    public void seeSawUp() {
        seeSawMotor.set(0.2);
    }

    public void seeSawDown() {
        seeSawMotor.set(-0.2);
    }

    public void seeSawStop() {
        seeSawMotor.set(0);
    }
}

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    CANSparkMax intakeMotor = new CANSparkMax(Ports.INTAKE_MOTOR, MotorType.kBrushless);
    // Solenoid deploy = new Solenoid(PneumaticsModuleType.CTREPCM,
    // Ports.INTAKE_DEPLOY);

    public Intake() {
    }

    public void runIntake(double speed) {
        // deploy.set(true);
        intakeMotor.set(-speed);
    }
}

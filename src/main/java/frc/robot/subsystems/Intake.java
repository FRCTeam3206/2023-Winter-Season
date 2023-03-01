package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    VictorSPX intakeMotor = new VictorSPX(Ports.INTAKE_MOTOR);
    Solenoid deploy = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.INTAKE_DEPLOY);

    public Intake() {
    }

    public void runIntake() {
        deploy.set(true);
        intakeMotor.set(ControlMode.PercentOutput, 1);
    }

    public void stopIntake() {
        deploy.set(false);
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
}

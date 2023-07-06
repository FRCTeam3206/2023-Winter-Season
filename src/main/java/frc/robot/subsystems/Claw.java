package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.*;

public class Claw extends SubsystemBase {
    Solenoid masterPiston = new Solenoid(PneumaticsModuleType.REVPH, Ports.CLAW_MASTER_PISTON);
    Solenoid secondaryPiston = new Solenoid(PneumaticsModuleType.REVPH, Ports.CLAW_SECOND_PISTON);

    public Claw() {
    }

    public void cone() {
        masterPiston.set(false);
        secondaryPiston.set(false);
    }

    public void cube() {
        masterPiston.set(false);
        secondaryPiston.set(true);
    }

    public void close() {
        cone();
    }

    public void open() {
        masterPiston.set(true);
        secondaryPiston.set(true);
    }
}

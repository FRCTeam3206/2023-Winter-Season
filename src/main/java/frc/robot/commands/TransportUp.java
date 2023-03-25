package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class TransportUp extends CommandBase {
    Intake intake;

    public TransportUp(Intake intake) {
        addRequirements(intake);
        this.intake = intake;
    }

    public void execute() {
        intake.setDeploy(false);
        intake.runIntake(.2);
        intake.setTransport(false);
    }
}

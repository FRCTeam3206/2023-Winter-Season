package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class SeeSawCommand extends CommandBase {
    private Intake intake;
    private double button;

    public SeeSawCommand(Intake intake, double button) {
        this.intake = intake;
        this.button = button;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (button == 0) {
            intake.seeSawDown();
        } else {
            intake.seeSawUp();
        }

    }

    @Override
    public void end(boolean interrupted) {
        intake.seeSawStop();
    }
}

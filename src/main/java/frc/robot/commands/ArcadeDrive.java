package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    private final Drivetrain m_drive;
    private final Supplier<Double> forwardPower;
    private final Supplier<Double> rotatePower;
    private final Supplier<Boolean> shift;

    public ArcadeDrive(Drivetrain subsystem, Supplier<Double> forwardPower, Supplier<Double> rotatePower,
            Supplier<Boolean> shift) {
        this.m_drive = subsystem;
        this.forwardPower = forwardPower;
        this.rotatePower = rotatePower;
        this.shift = shift;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.m_drive.arcadeDrive(-this.forwardPower.get(), this.rotatePower.get());
        m_drive.setShift(shift.get());
    }

    @Override
    public void end(boolean interrupted) {
    }

}
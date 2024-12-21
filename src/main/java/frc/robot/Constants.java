// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }



  public static class DriveConstants{
    public static final int kLeftMotor  = 1;
    public static final int kRightMotor = 2;

    public static final double maxSpeed = 0.5;
  }

  public static class IntakeConstants{
    public static final int kIntakeMotor   = 3;
    public static final int kIntakeQueue1  = 4;
    public static final int kIntakeQueue2  = 5;

    
    public static final int kQueue1SensorPort = 0;
    public static final int kQueue2SensorPort = 1;
  }

  public static class ShooterConstants{
    public static final int kHoodServo1Port = 8;
    public static final int kHoodServo2Port = 9;

    public static final int kShooterMainMotor = 7;
    public static final int kShooterFollowerMotor = 8;
  }
}

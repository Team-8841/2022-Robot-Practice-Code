// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.platform.DeviceType;
import com.ctre.phoenix.platform.PlatformJNI;
import com.ctre.phoenix.WPI_CallbackHelper;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

   


//speed controller groups
private CANSparkMax rightBackMotor;
private CANSparkMax leftBackMotor;
private CANSparkMax rightFrontMotor;
private CANSparkMax leftFrontMotor;


//drive train 
private DifferentialDrive m_drive;
private DifferentialDrive x_drive;

//other system
private VictorSPX qMotor;
private VictorSPX turretMotor;

private TalonSRX mainShootMotor;
private TalonSRX followShootMotor;
private TalonSRX intakeMotor;
private TalonSRX fireMotor;

private DigitalInput turretCW;
private DigitalInput turretCCW;
private DigitalInput index;
private DigitalInput intake;



//joystick
XboxController stick = new XboxController(0);

  @Override
  public void robotInit() {
    //motors
    rightBackMotor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
    leftBackMotor = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);
    rightFrontMotor = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
    leftFrontMotor = new CANSparkMax(10, CANSparkMax.MotorType.kBrushless);
    m_drive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
    x_drive = new DifferentialDrive(leftBackMotor, rightBackMotor);
qMotor = new VictorSPX(20);
turretMotor = new VictorSPX(1);
    intakeMotor = new TalonSRX(3);
    mainShootMotor = new TalonSRX(7);
    followShootMotor = new TalonSRX(8);
    fireMotor = new TalonSRX(5);

     //sensors
     turretCW = new DigitalInput(6);
     turretCCW = new DigitalInput(7);
     index = new DigitalInput(1);
     intake = new DigitalInput(0); //need channel number______----------
  }



  @Override
  public void robotPeriodic() {
   // rightBackMotor.set(stick.getY());
   // rightFrontMotor.set(stick.getY());
   // leftBackMotor.set(stick.getY());
   // leftFrontMotor.set(stick.getY());

           // Get joystick values
    double moveSpeed = -stick.getLeftX(); // Invert Y-axis
    double rotateSpeed = stick.getLeftY();
    boolean sensorStateCW = turretCW.get();  //declares a boolean for the turret clockwise limit sensor
    boolean sensorStateCCW = turretCCW.get(); //declares a boolean for the turret counterclockwise limit sensor
    boolean sensorStateIndex = index.get();
    boolean sensorStateIntake = intake.get();


    // Arcade drive
    m_drive.arcadeDrive(moveSpeed, rotateSpeed);
     x_drive.arcadeDrive(moveSpeed, rotateSpeed);

     

//leftbump button runs the intake in
if(stick.getLeftBumper()){
  intakeMotor.set(ControlMode.PercentOutput, 0.6);
  qMotor.set(ControlMode.PercentOutput, -0.6);
  if(sensorStateIndex){
    fireMotor.set(ControlMode.PercentOutput, 0.6);
    if(sensorStateIntake){
      intakeMotor.set(ControlMode.PercentOutput, 0.6);
      qMotor.set(ControlMode.PercentOutput,-0.6);
    }
  }else{
    fireMotor.set(ControlMode.PercentOutput, 0.0);
  }
}else if(stick.getRightBumper()){
  qMotor.set(ControlMode.PercentOutput, 0);
  fireMotor.set(ControlMode.PercentOutput, 0);
}else{
  qMotor.set(ControlMode.PercentOutput, 0);
  intakeMotor.set(ControlMode.PercentOutput, 0);
  fireMotor.set(ControlMode.PercentOutput, 0);
}




//A button spins up the shooter 30%
if (stick.getAButton()){
followShootMotor.set(ControlMode.PercentOutput, -.3);
mainShootMotor.set(ControlMode.PercentOutput, .3);
}
//B button spins up the shooter 60%
if (stick.getBButton()){
  followShootMotor.set(ControlMode.PercentOutput, -.6);
  mainShootMotor.set(ControlMode.PercentOutput, .6);
}
//Y button spins up the shooter 90%
if (stick.getYButton()){
  followShootMotor.set(ControlMode.PercentOutput, -.9);
  mainShootMotor.set(ControlMode.PercentOutput, .9);
}
//x button stops the shooter
if (stick.getXButton()){
followShootMotor.set(ControlMode.PercentOutput, 0);
mainShootMotor.set(ControlMode.PercentOutput, 0);
}

//right trigger turns turret right and left trigger turns turret left unless the turret IR sensors are triggered
if (stick.getRightTriggerAxis()>.5 && sensorStateCCW){
turretMotor.set(ControlMode.PercentOutput, .2);
}
else if (stick.getLeftTriggerAxis()>.5 && sensorStateCW){
turretMotor.set(ControlMode.PercentOutput, -.2);
}
else {
turretMotor.set(ControlMode.PercentOutput, 0);
}
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {



   // drivetrain.arcadeDrive(stick.getY(), stick.getZ());
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}

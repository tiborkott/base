package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		boolean alarmed = false;

		if(speedLimit < 0 || speedLimit > 500){
			//Absolute margin: If the new speed limit is under 0, or over 500.
			user.setAlarmState(true);
			alarmed = true;
		}

		if((double)(speedLimit / this.speedLimit) < 0.5 ){
			//Relative margin: If the new speed limit is more than 50% slower than the actual reference
			//speed (e.g., 150 to 50 is an alarming situation, because 50 is more than 50% less than 150).
			user.setAlarmState(true);
			alarmed = true;
		}

		if(!alarmed){
			this.speedLimit = speedLimit;
			controller.setSpeedLimit(speedLimit);
		}

	}

}

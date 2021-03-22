package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainController mTrainController;
    TrainUser mTrainUser;
    TrainSensorImpl TrainSensor;

    @Before
    public void before() {
        mTrainController = mock(TrainController.class);
        mTrainUser = mock(TrainUser.class);
        TrainSensor = new TrainSensorImpl(mTrainController, mTrainUser);
    }

    @Test
    public void AbsoluteMarginMinTest() {
        TrainSensor.overrideSpeedLimit(-1);
        //triggers both absolute margin and relative margin, two is expected
        verify(mTrainUser, times(2)).setAlarmState(true);
    }

    @Test
    public void AbsoluteMarginMaxTest() {
        TrainSensor.overrideSpeedLimit(600);
        verify(mTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void RelativeMarginTest() {
        TrainSensor.overrideSpeedLimit(1);
        verify(mTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void InnerMarginTest() {
        TrainSensor.overrideSpeedLimit(400);
        verify(mTrainUser, times(0)).setAlarmState(false);
    }
}

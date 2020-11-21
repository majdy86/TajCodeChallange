package org.assignmentSeera.apiEngine.model.requests;

import org.assignmentSeera.dataProvider.DataManipulationBaseObject;

public class CheckInOutDate {
    public String checkin = new DataManipulationBaseObject().getValidStringDate(1,10);
    public String checkout =  new DataManipulationBaseObject().getValidStringDate(11,20);

}

package org.assignmentSeera.apiEngine.model.requests;

import org.assignmentSeera.dataProvider.DataManipulationBaseObject;
import org.assignmentSeera.dataProvider.GuestType;


public class CHDGuest {

    public String type = GuestType.CHD.toString();
    public int age = new DataManipulationBaseObject().getRandomIntegerRange(1,18) ;

}

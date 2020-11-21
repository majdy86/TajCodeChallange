package org.assignmentSeera.apiEngine.model.requests;

import org.assignmentSeera.dataProvider.DataManipulationBaseObject;
import org.assignmentSeera.dataProvider.GuestType;


public class CHDGuest {

    public String type;
    public int age;

    public CHDGuest(){
        type = GuestType.CHD.toString();
        age = age = new DataManipulationBaseObject().getRandomIntegerRange(1,18);
    }
}

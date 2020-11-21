package org.assignmentSeera.apiEngine.model.requests;

import org.assignmentSeera.dataProvider.DataManipulationBaseObject;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public CheckInOutDate dates;

    // sat static according to the assignment documentation
    public String destination = "paris";
    public List<Guests> room = new ArrayList<>();

    //sat static according to the assignment documentation
    public String placeId = "ChIJD7fiBh9u5kcRYJSMaMOCCwQ";

    public Room() {
        CHDGuest CHDGuest = new CHDGuest();
        getNumberOfGuests(new DataManipulationBaseObject().getRandomIntegerRange(1, 10));
        dates = new CheckInOutDate();
    }

    public Room(int numberOfGuests) {
        CHDGuest CHDGuest = new CHDGuest();
        getNumberOfGuests(numberOfGuests);
        dates = new CheckInOutDate();
    }


    /**
     * guests generator
     *
     * @param numOfGuests the number of guests you  are willing to send in your request body
     */
    public void getNumberOfGuests(int numOfGuests) {
        Guests guests = new Guests();
        if (numOfGuests < 1) {
            this.room.add(guests);
        } else {
            while (numOfGuests > 1) {
                guests = new Guests();
                room.add(guests);
                numOfGuests -= 1;
            }
        }
    }
}

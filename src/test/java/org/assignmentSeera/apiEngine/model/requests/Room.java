package org.assignmentSeera.apiEngine.model.requests;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public CheckInOutDate dates;
    public String destination = "paris";
    public List<Guests> room = new ArrayList<>();
    public String placeId = "ChIJD7fiBh9u5kcRYJSMaMOCCwQ";

    public Room() {
        CHDGuest CHDGuest = new CHDGuest();
        Guests guests = new Guests(3);
        guests.guest.add(CHDGuest);
        room.add(guests);
        dates = new CheckInOutDate();
    }

    public Room(int numberOfGuests) {
        CHDGuest CHDGuest = new CHDGuest();
        Guests guests = new Guests(numberOfGuests);
        guests.guest.add(CHDGuest);
        room.add(guests);
        dates = new CheckInOutDate();
    }


}

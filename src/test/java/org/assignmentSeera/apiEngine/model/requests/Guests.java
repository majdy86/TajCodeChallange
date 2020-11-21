package org.assignmentSeera.apiEngine.model.requests;

import java.util.ArrayList;
import java.util.List;

public class Guests {
    public List<Object> guest;

    Guests(int x) {
        guest = new ArrayList<>();
        this.guest = numberOfGuests(x);
    }

    List<Object> numberOfGuests(int numOfGuests) {
        CHDGuest chdGuest = new CHDGuest();
        ADTGuest adtGuest = new ADTGuest();

        if (numOfGuests < 1) {
            this.guest.add(chdGuest);
            this.guest.add(adtGuest);
        } else {
            while (numOfGuests > 1) {
                guest.add(chdGuest);
                guest.add(adtGuest);
                numOfGuests -= 1;
            }
        }
        return guest;
    }

}

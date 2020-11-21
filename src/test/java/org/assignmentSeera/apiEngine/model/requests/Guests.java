package org.assignmentSeera.apiEngine.model.requests;

import org.assignmentSeera.dataProvider.DataManipulationBaseObject;

import java.util.ArrayList;
import java.util.List;

public class Guests {
    public List<Object> guest;

    Guests() {
        guest = new ArrayList<>();
        this.guest = numberOfGuests(new DataManipulationBaseObject().getRandomIntegerRange(1, 10));
    }

    Guests(int numberOfGuestsType) {
        guest = new ArrayList<>();
        this.guest = numberOfGuests(numberOfGuestsType);
    }

    List<Object> numberOfGuests(int numOfGuestsTypes) {
        CHDGuest chdGuest = new CHDGuest();
        ADTGuest adtGuest = new ADTGuest();

        if (numOfGuestsTypes < 1) {
            this.guest.add(chdGuest);
            this.guest.add(adtGuest);
        } else {
            while (numOfGuestsTypes > 1) {
                adtGuest = new ADTGuest();
                adtGuest = new ADTGuest();
                guest.add(chdGuest);
                guest.add(adtGuest);
                numOfGuestsTypes -= 1;
            }
        }
        return guest;
    }
}

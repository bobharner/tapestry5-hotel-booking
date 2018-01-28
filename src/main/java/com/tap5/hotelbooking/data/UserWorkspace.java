package com.tap5.hotelbooking.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tap5.hotelbooking.entities.Booking;
import com.tap5.hotelbooking.entities.Hotel;
import com.tap5.hotelbooking.entities.User;

/**
 * Use this object to store user's booking. It also provides all the method to handle booking in
 * progress for the current user. This object is stored in session when the user starts to book an
 * hotel.
 * 
 * @author karesti
 */
public class UserWorkspace
{
    private Booking current;

    private List<Booking> notConfirmed = new ArrayList<Booking>();

    /**
     * Get the list of bookings that are not yet confirmed
     * @return the list, possibly empty
     */
    public List<Booking> getNotConfirmed()
    {
        return notConfirmed;
    }

    /**
     * Get the currently-in-progress booking, if any
     * @return the booking, or null if none
     */
    public Booking getCurrent()
    {
        return current;
    }

    /**
     * Start booking the given hotel for the given user
     * @param hotel the hotel to book
     * @param user the current user
     */
    public void startBooking(Hotel hotel, User user, Date checkInDate, Date checkOutDate)
    {
        Booking booking = new Booking(hotel, user, checkInDate, checkOutDate);
        this.current = booking;
        notConfirmed.add(booking);
    }

    /**
     * Re-activate the booking with the given ID if it is found in the list of
     * in-progress bookings
     * @param bookId the id of the booking to reactivate
     * @return the reactivated booking, or null if none was found
     */
    public Booking restoreBooking(Long bookId)
    {
        Booking restoredBooking = null;

        for (Booking booking : notConfirmed)
        {
            if (bookId.equals(booking.getHotel().getId()))
            {
                restoredBooking = booking;
                break;
            }
        }

        this.current = restoredBooking;

        return restoredBooking;
    }

    /**
     * Remove the given booking from the list of in-progress bookings
     * @param booking the booking to cancel
     */
    public void cancelCurrentBooking(Booking booking)
    {
        removeCurrentBooking(booking);
    }

    /**
     * Confirm the given booking (and remove it from the list of in-progress
     * bookings
     * @param booking the booking to confirm
     */
    public void confirmCurrentBooking(Booking booking)
    {
        removeCurrentBooking(booking);
    }

    /**
     * Remove the given booking from the list of in-progress bookings
     * @param booking the booking to remove
     */
    private void removeCurrentBooking(Booking booking)
    {
        notConfirmed.remove(booking);
        this.current = null;
    }

}

package com.tap5.hotelbooking.pages;

import com.tap5.hotelbooking.dal.CrudServiceDAO;
import com.tap5.hotelbooking.data.BedType;
import com.tap5.hotelbooking.data.Months;
import com.tap5.hotelbooking.data.UserWorkspace;
import com.tap5.hotelbooking.data.Years;
import com.tap5.hotelbooking.entities.Booking;
import com.tap5.hotelbooking.entities.Hotel;
import com.tap5.hotelbooking.entities.PaymentType;
import com.tap5.hotelbooking.services.Authenticator;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.ThreadLocale;

import java.util.Calendar;

/**
 * This page implements booking process for a give hotel.
 * 
 * @author ccordenier
 */
public class Book
{
    @SessionState
    @Property
    private UserWorkspace userWorkspace;

    @Property
    @PageActivationContext
    private Hotel hotel;

    @Inject
    private Block bookBlock, confirmBlock;

    @Inject
    private Messages messages;

    @Inject
    private CrudServiceDAO dao;

    @Inject
    private Authenticator authenticator;

    @Inject
    private ThreadLocale threadLocale;

    @InjectComponent
    private Form bookingForm;

    @InjectComponent
    private ClientElement creditCardFields, bankAccountFields;

    @Property
    @Persist()
    private Booking booking;

    @Persist
    private boolean confirmationStep;

    @Property
    private SelectModel bedType = new BedType();

    @Property
    private SelectModel years = new Years();

    @Property
    private SelectModel months;

    /**
     * Respond to the form component's "prepare for render" event. We create the months
     * select model here because it needs to be rebuilt if the locale changes
     */
    void onPrepareForRender() {
        months = new Months(threadLocale.getLocale());
    }

    /**
     * Get the current step
     * 
     * @return confirmation or booking bloc
     */
    public Block getStep()
    {
        return confirmationStep ? confirmBlock : bookBlock;
    }

    /**
     * Get a heading string to display at the top of the page. This varies
     * depending on what step we're at.
     * @return the heading string, potentially localized
     */
    public String getHeading()
    {
        String key = confirmationStep ? "heading.pleaseConfirm" : "heading.bookThisRoom";
        return messages.get(key);
    }

    public String getSecuredCardNumber()
    {

        return booking.getCreditCardNumber().substring(12);
    }

    @OnEvent(value = EventConstants.ACTIVATE)
    @Log
    public Object setupBooking(EventContext eventContext)
    {
        // check whether the activation context (URL path) has any hotel id in it
        if (eventContext.getCount() == 0) // no activation parameter
        {
            booking = userWorkspace.getCurrent();
            if (booking==null) {
                confirmationStep = false;
            } else {
                confirmationStep = booking.getStatus();
            }
        }
        else
        {
            // a booking ID was given, so reactivate it
            Long hotelId = eventContext.get(Long.class, 0);
            booking = userWorkspace.restoreBooking(hotelId);
        }

        if (booking == null)
        {
            return Index.class;
        }
        else
        {
            if (!booking.hasUser() && authenticator.isLoggedIn())
            {
                booking.setUser(authenticator.getLoggedUser());
            }
            confirmationStep = booking.getStatus();
            return null;
        }
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "bookingForm")
    public void validateBooking()
    {
        if (!booking.hasUser())
        {
            throw new RuntimeException("Can't save booking because user is unknown");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (booking.getCheckinDate().before(calendar.getTime()))
        {
            bookingForm.recordError(messages.get("error.checkInNotFutureDate"));
            return;
        }
        else if (!booking.getCheckinDate().before(booking.getCheckoutDate()))
        {
            bookingForm.recordError(messages.get("error.checkOutBeforeCheckIn"));
            return;
        }
        
        switch (booking.getPaymentType())
        {
            case BANK_ACCOUNT:
                bookingForm.recordError("message.get(error.paymentMethodNotSupported");
                break;
            case CREDIT_CARD:
                break;
            default:
                break;
            
        }

        userWorkspace.getCurrent().setStatus(true);
    }

    @OnEvent(value = EventConstants.FAILURE, component = "bookingForm")
    public void onFailureFromBookingForm() {
        booking.setStatus(false);
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "confirmForm")
    public Object confirm()
    {
        // Create
        dao.create(booking);

        userWorkspace.confirmCurrentBooking(booking);

        booking = null;

        // Return to search
        return Index.class;
    }

    @OnEvent(value = "cancelConfirm")
    @Log
    public void cancelConfim()
    {
        booking.setStatus(false);
    }

    @OnEvent(value = "cancelBooking")
    @Log
    public Object cancelBooking()
    {
        userWorkspace.cancelCurrentBooking(booking);

        booking = null;

        return Index.class;
    }
    
    public PaymentType getCreditCard()
    {
        return PaymentType.CREDIT_CARD;
    }

    public PaymentType getBankAccount()
    {
        return PaymentType.BANK_ACCOUNT;
    }

    public boolean isPayingWithCreditCard()
    {
        return PaymentType.CREDIT_CARD.equals(booking.getPaymentType());
    }

    public boolean isPayingWithBankAccount()
    {
        return PaymentType.BANK_ACCOUNT.equals(booking.getPaymentType());
    }

    public boolean isSubmitDisabled()
    {
        return isPayingWithBankAccount();
    }
}

package com.tap5.hotelbooking.data;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

/**
 * Used to display a list of months in card expiracy select list.
 *
 * @author ccordenier
 *
 */
public class Months extends AbstractSelectModel
{

    private List<OptionModel> options = new ArrayList<OptionModel>();

    public Months(Locale locale)
    {
        int monthNum = 0;
        String[] monthNames = new DateFormatSymbols(locale).getMonths();
        for (String monthName : monthNames)
        {
            options.add(new OptionModelImpl(monthName, monthNum++));
        }
    }

    public List<OptionGroupModel> getOptionGroups()
    {
        return null;
    }

    public List<OptionModel> getOptions()
    {
        return options;
    }

    @Override
    public String toString()
    {
        return "Months [options=" + options + "]";
    }

}
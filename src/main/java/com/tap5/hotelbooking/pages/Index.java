package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.hibernate.HibernateGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.tap5.hotelbooking.annotations.AnonymousAccess;
import com.tap5.hotelbooking.data.SearchCriteria;
import com.tap5.hotelbooking.entities.Hotel;

/**
 * Lookup for Hotels.
 * 
 * @author ccordenier
 */
@AnonymousAccess
public class Index
{
    @Inject
    private Session session;

    @InjectComponent
    private Zone result;

    @SessionState
    @Property
    private SearchCriteria criteria;

    @Property
    private GridDataSource source = new HotelDataSource(session, Hotel.class);

    @Property
    private Hotel currentHotel;

    /**
     * This datasource is used by the Tapestry Grid component to search and paginate
     * 
     * @author ccordenier
     */
    private final class HotelDataSource extends HibernateGridDataSource
    {
        private HotelDataSource(Session session, @SuppressWarnings("rawtypes") Class entityType)
        {
            super(session, entityType);
        }

        @Override
        public int getAvailableRows()
        {
            return criteria.getSearchPattern() == null ? 0 : super.getAvailableRows();
        }

        @Override
        protected void applyAdditionalConstraints(Criteria crit)
        {
            // allow "*" to match everything
            if ("*".equals(criteria.getSearchPattern()))
            {
                return;
            }
            Criterion searchHotel = Restrictions.ilike("name", criteria.getSearchPattern());
            Criterion searchCity = Restrictions.ilike("city", criteria.getSearchPattern());
            crit.add(Restrictions.or(searchHotel, searchCity));
        }
    }

    /**
     * TODO: Move this into a hotel query service
     */
    @OnEvent(value = EventConstants.SUCCESS)
    Object searchHotels()
    {
        return result.getBody();
    }
    
    Object onAction()
    {
        return result.getBody();
    }

}

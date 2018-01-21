package com.teslaboat.viewModels.reservation;

import com.teslaboat.model.db.Location;

import java.util.Date;
import java.util.List;

public class BoatSearchForm {
    private Long selectedDestination;
    private Date selectedDate;
    private List<Location> destinations;
    private List<BoatTypeModel> boatTypes;

    public BoatSearchForm() {}
    public BoatSearchForm(Long selectedDestination, Date selectedDate, List<Location> destinations, List<BoatTypeModel> boatTypes)
    {
        this.selectedDestination = selectedDestination;
        this.selectedDate = selectedDate;
        this.destinations = destinations;
        this.boatTypes = boatTypes;
    }

    public Long getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(Long selectedDestination) {
        this.selectedDestination = selectedDestination;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<Location> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Location> destinations) {
        this.destinations = destinations;
    }

    public List<BoatTypeModel> getBoatTypes() {
        return boatTypes;
    }

    public void setBoatTypes(List<BoatTypeModel> boatTypes) {
        this.boatTypes = boatTypes;
    }
}

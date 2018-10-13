package ro.andreidobrescu.jclusterspiderifier.googlemaps.model;

import com.google.android.gms.maps.model.LatLng;

public class Restaurant implements ISpiderifiableClusterItem
{
    private String name;
    private double latitude;
    private double longitude;
    private double previousLatitude;
    private double previousLongitude;

    public Restaurant(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getPreviousLatitude()
    {
        return previousLatitude;
    }

    public void setPreviousLatitude(double previousLatitude)
    {
        this.previousLatitude = previousLatitude;
    }

    public double getPreviousLongitude()
    {
        return previousLongitude;
    }

    public void setPreviousLongitude(double previousLongitude)
    {
        this.previousLongitude = previousLongitude;
    }

    @Override
    public LatLng getPosition()
    {
        return new LatLng(latitude, longitude);
    }

    @Override
    public String getTitle()
    {
        return name;
    }

    @Override
    public String getSnippet()
    {
        return name;
    }

    @Override
    public double spGetLat()
    {
        return latitude;
    }

    @Override
    public double spGetLng()
    {
        return longitude;
    }

    @Override
    public void spUpdateLatLng(double latitude, double longitude)
    {
        this.previousLatitude=this.latitude;
        this.previousLongitude=this.longitude;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    @Override
    public void spRevertUpdateLatLng()
    {
        this.latitude=this.previousLatitude;
        this.longitude=this.previousLongitude;
    }
}

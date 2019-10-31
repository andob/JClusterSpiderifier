package ro.andreidobrescu.jclusterspiderifier.googlemaps.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import ro.andreidobrescu.jclusterspiderifier.ISpiderifiablePin;
import ro.andreidobrescu.jclusterspiderifier.ISpiderifiablePinProxy;

public class Restaurant implements ClusterItem, ISpiderifiablePin
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
    public ISpiderifiablePinProxy getSpiderifiablePinProxy()
    {
        return new ISpiderifiablePinProxy()
        {
            @Override
            public double getLat()
            {
                return Restaurant.this.getLatitude();
            }

            @Override
            public double getLng()
            {
                return Restaurant.this.getLongitude();
            }

            @Override
            public void updateLatLng(double latitude, double longitude)
            {
                Restaurant restaurant=Restaurant.this;
                restaurant.previousLatitude=restaurant.latitude;
                restaurant.previousLongitude=restaurant.longitude;
                restaurant.latitude=latitude;
                restaurant.longitude=longitude;
            }

            @Override
            public void revertUpdateLatLng()
            {
                Restaurant restaurant=Restaurant.this;
                restaurant.latitude=restaurant.previousLatitude;
                restaurant.longitude=restaurant.previousLongitude;
            }
        };
    }
}

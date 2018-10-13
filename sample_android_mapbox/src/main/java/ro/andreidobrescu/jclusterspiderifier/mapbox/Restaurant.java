package ro.andreidobrescu.jclusterspiderifier.mapbox;

import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterItem;

public class Restaurant //implements ClusterItem//, ISpiderifiablePin
{
    private String name;
    private double latitude;
    private double longitude;
    private double previousLatitude;
    private double previousLongitude;

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
}

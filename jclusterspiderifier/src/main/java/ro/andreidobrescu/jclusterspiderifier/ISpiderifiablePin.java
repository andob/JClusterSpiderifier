package ro.andreidobrescu.jclusterspiderifier;

public interface ISpiderifiablePin
{
    double spGetLat();
    double spGetLng();
    void spUpdateLatLng(double latitude, double longitude);
    void spRevertUpdateLatLng();
}

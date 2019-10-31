package ro.andreidobrescu.jclusterspiderifier;

public interface ISpiderifiablePinProxy
{
    double getLat();
    double getLng();
    void updateLatLng(double latitude, double longitude);
    void revertUpdateLatLng();
}

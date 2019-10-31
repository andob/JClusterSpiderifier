package ro.andreidobrescu.jclusterspiderifier;

import java.util.Collection;

public class ClusterSpiderifier
{
    private int layer;
    private int numberOfPinsInLayer;
    private int indexInLayer;
    private double distanceTimesLayer;
    private double radius = 0.0007;

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public void spiderify(Collection<? extends ISpiderifiablePin> pins)
    {
        ISpiderifiablePinProxy firstPin=pins.iterator().next().getSpiderifiablePinProxy();
        double centerLat=firstPin.getLat();
        double centerLng=firstPin.getLng();

        setLayer(1);

        for (ISpiderifiablePin pin : pins)
        {
            double rotation=rotationForPinInLayer();
            double lng=distanceTimesLayer*Math.sin(rotation)+centerLng;
            double lat=distanceTimesLayer*Math.cos(rotation)+centerLat;

            pin.getSpiderifiablePinProxy().updateLatLng(lat, lng);

            indexInLayer++;

            if (indexInLayer==numberOfPinsInLayer)
            {
                //increment layer
                setLayer(layer+1);
            }
        }
    }

    private void setLayer(int layer)
    {
        this.layer=layer;
        this.numberOfPinsInLayer=numberOfPinsInLayer(layer);
        this.indexInLayer=0;
        this.distanceTimesLayer=layer* radius;
    }

    private int numberOfPinsInLayer(int layer)
    {
        return 4*(1+layer);
    }

    private double rotationForPinInLayer()
    {
        if (indexInLayer==0)
            return 0;
        return indexInLayer*2*Math.PI/numberOfPinsInLayer;
    }

    public void unspiderify(Collection<? extends ISpiderifiablePin> items)
    {
        for (ISpiderifiablePin item : items)
            item.getSpiderifiablePinProxy().revertUpdateLatLng();
    }
}

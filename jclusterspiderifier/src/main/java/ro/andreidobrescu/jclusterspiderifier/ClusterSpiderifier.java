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

    public void spiderify(Collection<? extends ISpiderifiablePin> items)
    {
        ISpiderifiablePin firstItem=items.iterator().next();
        double centerLat=firstItem.spGetLat();
        double centerLng=firstItem.spGetLng();

        setLayer(1);

        for (ISpiderifiablePin item : items)
        {
            double rotation=rotationForPinInLayer();
            double lng=distanceTimesLayer*Math.sin(rotation)+centerLng;
            double lat=distanceTimesLayer*Math.cos(rotation)+centerLat;

            item.spUpdateLatLng(lat, lng);

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
        {
            item.spRevertUpdateLatLng();
        }
    }
}

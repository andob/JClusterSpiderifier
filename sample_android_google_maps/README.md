## JClusterSpiderifier sample Google Maps integration

In this sample integration, the user can zoom in the map towards a cluster, and then tap the cluster to spread the markers. When zooming out, the marker spider web will collapse into a cluster.

### 1. Setup your model

Your data model must extend both Google Maps clustering plugin's ClusterItem interface and JClusterSpiderifier's ISpiderifiablePin. Create an interface ISpiderifiableClusterItem:
```java
public interface ISpiderifiableClusterItem extends ClusterItem, ISpiderifiablePin
{
}
```
And implement it in your model:
```java
public class Restaurant implements ISpiderifiableClusterItem
{
    private String name;
    private double latitude;
    private double longitude;
    private double previousLatitude;
    private double previousLongitude;

    //.......................................
    //ISpiderifiableItem's methods:

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
```
Your model must "remember" its previous location. When spiderifing pins, the library will set latitude / longitude properties via spUpdateLatLng method. Unspiderifiering (transforming the marker spider web back again into a cluster) uses spRevertUpdateLatLng to set the model's original location.

### 2. Setup MapActivity

```java
public abstract class BaseMapActivity<MODEL extends ISpiderifiableClusterItem> extends AppCompatActivity implements
        ClusterManager.OnClusterItemInfoWindowClickListener<MODEL>,
        ClusterManager.OnClusterClickListener<MODEL>,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnInfoWindowClickListener
{
    public static final int CLUSTER_SPIDERIFY_ZOOM_THRESHOLD = 15;

    protected ClusterManager<MODEL> clusterManager;
    protected ClusterSpiderifier clusterSpiderifier=new ClusterSpiderifier();
    protected Map<Marker, MODEL> spiderifiedMarkers=new HashMap<>();
    protected GoogleMap map;

    //.....
```

### 3. Setup callbacks

#### 3.1. When user zooms in enough and clicks a cluster:

Remove the pins from cluster manager, spiderify them, and then add markers with the spiderified data. I saved the link between the map marker and the data model in the spiderifiedMarkers map.
```java
@Override
public boolean onClusterClick(Cluster<MODEL> cluster)
{
    if (map.getCameraPosition().zoom>CLUSTER_SPIDERIFY_ZOOM_THRESHOLD)
    {
        Collection<MODEL> items=cluster.getItems();
        for (MODEL item : items)
            clusterManager.removeItem(item);
        clusterManager.cluster();

        clusterSpiderifier.spiderify(items);

        for (MODEL item : items)
        {
            Marker marker=map.addMarker(new MarkerOptions()
                    .title(item.getTitle())
                    .snippet(item.getSnippet())
                    .position(item.getPosition()));

            spiderifiedMarkers.put(marker, item);
        }
    }

    return false;
}
```

#### 3.2. When the user zooms out:

If there are spiderified markers on map, remove them, unspiderify the data and restore the data to the cluster manager:
```java
@Override
public void onCameraIdle()
{
    clusterManager.onCameraIdle();

    if (map.getCameraPosition().zoom<CLUSTER_SPIDERIFY_ZOOM_THRESHOLD)
    {
        if (spiderifiedMarkers.size()>0)
        {
            Set<Marker> markers=spiderifiedMarkers.keySet();
            for (Marker marker : markers)
                marker.remove();

            Collection<MODEL> items=spiderifiedMarkers.values();
            clusterSpiderifier.unspiderify(items);

            clusterManager.addItems(items);
            clusterManager.cluster();

            spiderifiedMarkers.clear();
        }
    }
}
```

#### 3.3. When the user clicks on marker's info window popup.. if the marker is not managed by the cluster manager:

We must have a single entry point for "info window clicked" events. The markers from clusters, managed by ClusterManager will respond to the ```ClusterManager.OnClusterItemInfoWindowClickListener``` event, whereas the markers added outside of ClusterManager context will respond to the ```GoogleMap.OnInfoWindowClickListener``` event. Thus, when ```onInfoWindowClick``` event is invoked by a marker that is not managed by the ClusterManager, we will route the event to ```onClusterItemInfoWindowClick```
```java
@Override
public void onInfoWindowClick(@NonNull Marker marker)
{
    MODEL item=spiderifiedMarkers.get(marker);
    if (item!=null)
    {
        onClusterItemInfoWindowClick(item);
    }
    else
    {
        clusterManager.onInfoWindowClick(marker);
    }
}
```

#### 3.4. When the user clicks on marker's info window popup:

```java
@Override
public void onClusterItemInfoWindowClick(Restaurant restaurant)
{
    Toast.makeText(this, "Clicked on "+restaurant.getName(), Toast.LENGTH_SHORT).show();
}
```
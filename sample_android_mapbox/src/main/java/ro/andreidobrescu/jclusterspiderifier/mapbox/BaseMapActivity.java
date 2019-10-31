package ro.andreidobrescu.jclusterspiderifier.mapbox;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.Cluster;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterItem;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterManagerPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ro.andreidobrescu.jclusterspiderifier.ClusterSpiderifier;
import ro.andreidobrescu.jclusterspiderifier.ISpiderifiablePin;

public abstract class BaseMapActivity<MODEL extends ClusterItem & ISpiderifiablePin> extends AppCompatActivity implements
        ClusterManagerPlugin.OnClusterItemInfoWindowClickListener<MODEL>,
        ClusterManagerPlugin.OnClusterClickListener<MODEL>,
        MapboxMap.OnCameraIdleListener,
        MapboxMap.OnInfoWindowClickListener
{
    public static final int CLUSTER_SPIDERIFY_ZOOM_THRESHOLD = 15;

    protected ClusterManagerPlugin<MODEL> clusterManager;
    protected ClusterSpiderifier clusterSpiderifier;
    protected Map<Marker, MODEL> spiderifiedMarkers;
    protected MapboxMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(provideLayout());

        String mapBoxKey="YOUR API KEY";
        Mapbox.getInstance(this, mapBoxKey);

        MapboxMapOptions options=new MapboxMapOptions();
        options.styleUrl(Style.MAPBOX_STREETS);

        SupportMapFragment fragment=SupportMapFragment.newInstance(options);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        fragment.getMapAsync(this::onMapReady);
    }

    private void onMapReady(MapboxMap map)
    {
        this.map=map;

        clusterManager = new ClusterManagerPlugin<MODEL>(this, map);

        map.addOnCameraIdleListener(this);
        map.addOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
        map.setOnInfoWindowClickListener(this);
        clusterManager.setOnClusterClickListener(this);
        clusterManager.setOnClusterItemInfoWindowClickListener(this);

        clusterSpiderifier=new ClusterSpiderifier();
        spiderifiedMarkers=new HashMap<>();

        loadData();
    }

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

    @Override
    public void onCameraIdle()
    {
        if (map.getCameraPosition().zoom<CLUSTER_SPIDERIFY_ZOOM_THRESHOLD)
        {
            if (spiderifiedMarkers.size()>0)
            {
                Set<Marker> markers=spiderifiedMarkers.keySet();
                for (Marker marker : markers)
                    map.removeMarker(marker);

                Collection<MODEL> items=spiderifiedMarkers.values();
                clusterSpiderifier.unspiderify(items);

                clusterManager.addItems(items);
                clusterManager.cluster();

                spiderifiedMarkers.clear();
            }
        }
    }

    @Override
    public boolean onInfoWindowClick(@NonNull Marker marker)
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

        return false;
    }

    protected abstract void loadData();
    protected abstract int provideLayout();
}

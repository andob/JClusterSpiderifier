package ro.andreidobrescu.jclusterspiderifier.googlemaps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ro.andreidobrescu.jclusterspiderifier.ClusterSpiderifier;
import ro.andreidobrescu.jclusterspiderifier.googlemaps.model.ISpiderifiableClusterItem;

public abstract class BaseMapActivity<MODEL extends ISpiderifiableClusterItem> extends AppCompatActivity implements
        ClusterManager.OnClusterItemInfoWindowClickListener<MODEL>,
        ClusterManager.OnClusterClickListener<MODEL>,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnInfoWindowClickListener
{
    public static final int CLUSTER_SPIDERIFY_ZOOM_THRESHOLD = 15;

    protected ClusterManager<MODEL> clusterManager;
    protected ClusterSpiderifier clusterSpiderifier;
    protected Map<Marker, MODEL> spiderifiedMarkers;
    protected GoogleMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(provideLayout());

        SupportMapFragment fragment=new SupportMapFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        fragment.getMapAsync(this::onMapReady);
    }

    private void onMapReady(GoogleMap map)
    {
        this.map=map;

        clusterManager = new ClusterManager<>(this, map);

        map.setOnCameraIdleListener(this);
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

    protected abstract void loadData();
    protected abstract int provideLayout();
}

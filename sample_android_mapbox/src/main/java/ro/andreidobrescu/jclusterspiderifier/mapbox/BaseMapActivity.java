package ro.andreidobrescu.jclusterspiderifier.mapbox;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

public abstract class BaseMapActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(provideLayout());

        String mapBoxKey="";
        Mapbox.getInstance(this, mapBoxKey);

        MapboxMapOptions options=new MapboxMapOptions();
        options.styleUrl(Style.MAPBOX_STREETS);

        SupportMapFragment fragment=SupportMapFragment.newInstance(options);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        fragment.getMapAsync(this::loadData);
    }

    protected abstract void loadData(MapboxMap map);
    protected abstract int provideLayout();
}

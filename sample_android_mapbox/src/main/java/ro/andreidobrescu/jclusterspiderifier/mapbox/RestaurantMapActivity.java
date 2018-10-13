package ro.andreidobrescu.jclusterspiderifier.mapbox.restaurants;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import ro.andreidobrescu.jclusterspiderifier.mapbox.BaseMapActivity;
import ro.andreidobrescu.jclusterspiderifier.mapbox.R;

public class RestaurantMapActivity extends BaseMapActivity
{
    @Override
    protected void loadData(MapboxMap map)
    {

    }

    @Override
    protected int provideLayout()
    {
        return R.layout.activity_restaurant_map;
    }
}

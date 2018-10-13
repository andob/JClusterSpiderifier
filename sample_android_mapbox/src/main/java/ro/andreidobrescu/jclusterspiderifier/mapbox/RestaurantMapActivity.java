package ro.andreidobrescu.jclusterspiderifier.mapbox;

import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;

import java.util.LinkedList;
import java.util.List;

import ro.andreidobrescu.jclusterspiderifier.mapbox.model.Restaurant;

public class RestaurantMapActivity extends BaseMapActivity<Restaurant>
{
    @Override
    protected void loadData()
    {
        List<Restaurant> restaurants=new LinkedList<>();
        restaurants.add(new Restaurant("La placinte", 44.460905, 26.074435));

        for (int i=1; i<=17; i++)
            restaurants.add(new Restaurant("Restaurant "+i, 44.471220, 26.077582));

        clusterManager.addItems(restaurants);
        clusterManager.cluster();

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder()
                .include(new LatLng(44.473504, 26.072398))
                .include(new LatLng(44.459810, 26.078333))
                .build(), 0));
    }

    @Override
    protected int provideLayout()
    {
        return R.layout.activity_restaurant_map;
    }

    @Override
    public void onClusterItemInfoWindowClick(Restaurant restaurant)
    {
        Toast.makeText(this, "Clicked on "+restaurant.getName(), Toast.LENGTH_SHORT).show();
    }
}

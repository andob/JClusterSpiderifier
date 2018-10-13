package ro.andreidobrescu.jclusterspiderifier.googlemaps;

import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.LinkedList;
import java.util.List;

import ro.andreidobrescu.jclusterspiderifier.googlemaps.model.Restaurant;

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

        new Handler().postDelayed(() ->
        {
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder()
                    .include(new LatLng(44.473504, 26.072398))
                    .include(new LatLng(44.459810, 26.078333))
                    .build(), 0));
        }, 500);
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

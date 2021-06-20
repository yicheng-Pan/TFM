package yicheng.pan.tfm;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng site1 = new LatLng(31.252456616123524, 121.55607343500112);
        mMap.addMarker(new MarkerOptions().position(site1).title("SF Express"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(site1));

        LatLng site2 = new LatLng(32.38523861401119, 119.64976924096521);
        mMap.addMarker(new MarkerOptions().position(site2).title("SF Express"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(site2));

        LatLng site3 = new LatLng(25.62735041315964, 113.84898808412805);
        mMap.addMarker(new MarkerOptions().position(site3).title("SF Express"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(site3));

        LatLng site4 = new LatLng(34.87303181600051, 120.7044567240265);
        mMap.addMarker(new MarkerOptions().position(site4).title("SF Express"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(site4));

        LatLng site5 = new LatLng(31.789528557226284, 120.00133173531896);
        mMap.addMarker(new MarkerOptions().position(site5).title("SF Express"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(site5));


    }
}
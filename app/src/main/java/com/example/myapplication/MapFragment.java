package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static final int WHAT_DATA_OK = 1000;
    private MapView mapView;
    private Marker marker;

    public MapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.baidu_map_view);

        LatLng cenpt = new LatLng(22.255925,113.541112);  //?????????????????????-????????????
        LatLng library = new LatLng(22.255574,113.542556);//?????????????????????????????????
        MapStatus.Builder builder = new MapStatus.Builder();  //????????????

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark1);
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(cenpt);
        OverlayOptions textOption = new TextOptions().fontSize(36).fontColor(Color.BLACK).text("????????????").position(library);
        mapView.getMap().addOverlay(textOption);

        marker = (Marker) mapView.getMap().addOverlay(markerOptions);




        //??? marker ????????????????????????
        mapView.getMap().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(rootView.getContext().getApplicationContext(), "?????????maker", Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        builder.zoom(18.0f).target(cenpt);
        mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView. onResume ()?????????????????????????????????
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView. onPause ()?????????????????????????????????
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()?????????????????????????????????
        mapView.onDestroy();
    }
}
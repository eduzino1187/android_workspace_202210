package com.example.googlemapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment; //구글맵 전용 프레그먼트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //구글맵을 보여주기 위해서는 그냥  Fragment 가 아닌 MapFragment 를 사용함
        mapFragment = SupportMapFragment.newInstance();

        //액티비티가 프레그먼트를 제어하려면, 프레그먼트 메니저를 사용해야 한다.
        //메니저의 주 역할 (페이지에 대한 트랜잭션 처리)
        FragmentManager fragmentManager =getSupportFragmentManager();

        //트랜잭션 시작
        mapFragment.getMapAsync(this); // 리스너와 연결

        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, mapFragment);
        transaction.commit();

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //googleMap 객체 이용하여, 아이콘, 그림, 메세지.....
        //위도, 경도 설정
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(37.55555, 126.888889));
        options.title("여기가 맛집");
        googleMap.addMarker(options);
    }
}
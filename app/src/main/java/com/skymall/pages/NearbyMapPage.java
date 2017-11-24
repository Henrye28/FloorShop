package com.skymall.pages;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.skymall.R;
import com.skymall.fragments.NearbyStoreInfoFragment;

import java.util.Map;

public class NearbyMapPage extends AppCompatActivity implements CloudListener, SensorEventListener {

    private static final String CLOUD_AK = "amAh75f3xf7TTgwgSm3hKq56LOv7S6Gh";
    private static final String LTAG = NearbyMapPage.class.getSimpleName();

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private CloudManager mCloudManager;
    private String picURL;
    private String title;
    private String address;
    private LocationClient mLocClient;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private TextView goMap;

    private MyLocationData locData;

    public MyLocationListenner myListener = new MyLocationListenner();

    boolean isFirstLoc = true; // 是否首次定位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_map);
        setContentView(R.layout.nearby_map);
        Fresco.initialize(this);
        mCloudManager = CloudManager.getInstance();
        mCloudManager.init();
        mCloudManager.registerListener(NearbyMapPage.this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();//发送请求
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

    }


    private void nearByShopsOnClick(){
        NearbySearchInfo info = new NearbySearchInfo();
        info.ak = CLOUD_AK;
        info.geoTableId = 178788;
        info.radius = 30000;
        info.tags = "test";
        // info.location = mCurrentLon + "," + mCurrentLat;
        mCloudManager.nearbySearch(info);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
               @Override
               public boolean onMarkerClick(Marker marker) {

                   NearbyStoreInfoFragment rightFragment = new NearbyStoreInfoFragment();
                   FragmentManager fragmentManager = getFragmentManager();
                   FragmentTransaction transaction = fragmentManager.beginTransaction();
                   Log.d("Marker clicked2 :", marker.getPosition().toString() + " -- " + marker.getExtraInfo().get("storepic"));

                   transaction.replace(R.id.fragment, rightFragment);
                   //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
                   transaction.commit();
                   picURL = marker.getExtraInfo().getString("storepic");
                   address = marker.getExtraInfo().getString("address");
                   title = marker.getTitle();
                   return false;
               }
           }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        /* 释放监听者 */
        mCloudManager.unregisterListener();
        mCloudManager.destroy();
        mCloudManager= null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    public String[] getResult(){
        return new String[]{picURL,address,title};
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            Log.d(" --------received llll ", " latitude " + mCurrentLat + " longitude " + mCurrentLon);
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {

        Log.d(LTAG, "onGetSearchResult11, result length: " + cloudSearchResult.poiList );
        if (cloudSearchResult != null && cloudSearchResult.poiList != null
                && cloudSearchResult.poiList.size() > 0) {
            Log.d(LTAG, "onGetSearchResult, result length: " + cloudSearchResult.poiList.size());
            mBaiduMap.clear();
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            LatLng ll;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (CloudPoiInfo info : cloudSearchResult.poiList) {
                ll = new LatLng(info.latitude, info.longitude);
                Bundle bundle = new Bundle();
                Object classification = info.extras.get("classification");
                Object storeID = info.extras.get("storeid");
                Object storePicURL = info.extras.get("storepic");

                if(classification != null) {
                    bundle.putString("classification", classification.toString());
                }
                if(storePicURL != null) {
                    bundle.putString("storepic", storePicURL.toString());
                }
                if(storeID != null) {
                    bundle.putString("storeid", classification.toString());
                }
                bundle.putString("address",info.address);

                for(Map.Entry e : info.extras.entrySet()){
                    Log.d("entries : ", e.getKey() + " : " + e.getValue());
                }

                OverlayOptions oo = new MarkerOptions().icon(bd).position(ll).extraInfo(bundle).title(info.title);
                mBaiduMap.addOverlay(oo);
                builder.include(ll);
            }
            LatLngBounds bounds = builder.build();
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);
        }
    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }

    @Override
    public void onGetCloudRgcResult(CloudRgcResult cloudRgcResult, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

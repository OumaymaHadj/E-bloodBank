package hadj.tn.test.fragment.bottomMenuFragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import hadj.tn.test.R;

public class DonateBloodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonateBloodFragment() {
        // Required empty public constructor
    }


    MapView mMapView;
    private GoogleMap googleMap;


    public static DonateBloodFragment newInstance(String param1, String param2) {
        DonateBloodFragment fragment = new DonateBloodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_donate_blood, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.google_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng cnts = new LatLng(36.8050422668457 ,	10.157087326049805);
                googleMap.addMarker(new MarkerOptions().position(cnts).title("CNTS").snippet("71.562.957"));

                LatLng crtsSfax = new LatLng(34.7412168147 ,	10.7521731563 );
                googleMap.addMarker(new MarkerOptions().position(crtsSfax).title("CRTS Sfax").snippet("74.245.433"));

                LatLng crtsSousse = new LatLng(35.8298257 ,	10.6401929 );
                googleMap.addMarker(new MarkerOptions().position(crtsSousse).title("CRTS Sousse").snippet("73.224.411"));

                LatLng crtsJendouba = new LatLng(36.514290 ,	8.758959 );
                googleMap.addMarker(new MarkerOptions().position(crtsJendouba).title("CRTS Jendouba").snippet("78.604.569"));

                LatLng crtsGabes = new LatLng(33.863446 ,	10.115581 );
                googleMap.addMarker(new MarkerOptions().position(crtsGabes).title("CRTS Gabès").snippet("75.290.300"));

                LatLng crtsGafsa = new LatLng(34.420827 ,	8.796443 );
                googleMap.addMarker(new MarkerOptions().position(crtsGafsa).title("CRTS Gafsa").snippet("76.229.750"));

                LatLng CMTS = new LatLng(36.815668 ,	10.153031 );
                googleMap.addMarker(new MarkerOptions().position(CMTS).title("Centre Militaire de Transfusion Sanguine").snippet("71.562.465"));

                LatLng chuNicol = new LatLng(36.802442 ,	10.161240 );
                googleMap.addMarker(new MarkerOptions().position(chuNicol).title("CHU Charles Nicolle").snippet("71.562.777"));

                LatLng rabta = new LatLng(36.801989 ,	10.154634 );
                googleMap.addMarker(new MarkerOptions().position(rabta).title("CHU La Rabta").snippet("71.570.506"));

                LatLng thameur = new LatLng(36.786257 ,	10.176568 );
                googleMap.addMarker(new MarkerOptions().position(thameur).title("CHU Habib Thameur").snippet("71.397.171"));

                LatLng kassab = new LatLng(36.814667 ,	10.101781 );
                googleMap.addMarker(new MarkerOptions().position(kassab).title("Institut Mohamed Kassab d'orthopédie").snippet("71.606.920"));

                LatLng brg = new LatLng(35.770535 ,	10.833744 );
                googleMap.addMarker(new MarkerOptions().position(brg).title("CHU Fattouma Bourguiba").snippet("73.460.678"));

                LatLng sah = new LatLng(35.837260 ,	10.590537 );
                googleMap.addMarker(new MarkerOptions().position(sah).title("CHU Sahloul").snippet("73.367.451"));

                LatLng sfar = new LatLng(35.510215 ,	11.032702 );
                googleMap.addMarker(new MarkerOptions().position(sfar).title("CHU Tahar Sfar").snippet("73.671.579"));

                LatLng beja = new LatLng(36.721825 ,	9.179627 );
                googleMap.addMarker(new MarkerOptions().position(beja).title("Hopital régional de Béja").snippet("78.454.529"));

                LatLng biz = new LatLng(37.270013 ,	9.860530 );
                googleMap.addMarker(new MarkerOptions().position(biz).title("Hopital régional de Bizerte").snippet("72.443.500"));

                LatLng bali = new LatLng(36.642124 ,	10.490472 );
                googleMap.addMarker(new MarkerOptions().position(bali).title("Hopital de circonscription de Grombalia").snippet("72.257.825"));

                LatLng ba = new LatLng(33.888674 ,	10.844748 );
                googleMap.addMarker(new MarkerOptions().position(ba).title("Hopital régional Djerba").snippet("75.650.202"));

                LatLng kass = new LatLng(35.175236  ,	8.794023  );
                googleMap.addMarker(new MarkerOptions().position(kass).title("Hopital régional Kasserine").snippet("77.473.777"));

                LatLng st = new LatLng(33.718473 ,	8.973450 );
                googleMap.addMarker(new MarkerOptions().position(st).title("Hopital régional Kébili").snippet("75.494.588"));

                LatLng md = new LatLng(33.333487 ,	10.487763 );
                googleMap.addMarker(new MarkerOptions().position(md).title("Hopital régional Médenine").snippet("75.643.735"));

                LatLng mb = new LatLng(37.161427 ,	9.796376 );
                googleMap.addMarker(new MarkerOptions().position(mb).title("Hopital régional Menzel Bpurguiba").snippet("72.464.411"));

                LatLng mt = new LatLng(36.778556 ,	10.997653 );
                googleMap.addMarker(new MarkerOptions().position(mt).title("Hopital régional Menzel Temime").snippet("72.344.222"));

                LatLng ml = new LatLng(34.312559 ,	8.392042 );
                googleMap.addMarker(new MarkerOptions().position(ml).title("Hopital régional Metlaoui").snippet("76.240.400"));

                LatLng nb = new LatLng(36.453651 ,	10.731983 );
                googleMap.addMarker(new MarkerOptions().position(nb).title("Hopital régional Nabeul").snippet("72.224.848"));

                LatLng sb = new LatLng(35.023969 ,	9.465305 );
                googleMap.addMarker(new MarkerOptions().position(sb).title("Hopital régiona Sidi Bouzid").snippet("76.634.368"));

                LatLng sl = new LatLng(36.080622 ,	9.375338 );
                googleMap.addMarker(new MarkerOptions().position(sl).title("Hopital régional Siliana").snippet("78.870.019"));

                LatLng tt = new LatLng(32.984765 ,	10.460432 );
                googleMap.addMarker(new MarkerOptions().position(tt).title("Hopital régional Tataouine").snippet("75.870.624"));

                LatLng tr = new LatLng(33.916706 ,	8.129312 );
                googleMap.addMarker(new MarkerOptions().position(tr).title("Hopiatl régional Tozeur").snippet("76.453.047"));

                LatLng zz = new LatLng(33.541537 ,	11.088461 );
                googleMap.addMarker(new MarkerOptions().position(zz).title("Hopital régional de Zarzis").snippet("75.738.368"));


                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(33.584800 ,	9.322400)).zoom(6).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(getContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(getContext());

                        final View customLayout
                                = getLayoutInflater()
                                .inflate(
                                        R.layout.contact_dialog,
                                        null);
                        dialogEditPhone.setView(customLayout);
                        AlertDialog dialog = dialogEditPhone.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        TextView titre,numero;
                        titre = customLayout.findViewById(R.id.title);
                        numero = customLayout.findViewById(R.id.contacts);
                        titre.setText(marker.getTitle());
                        numero.setText(marker.getSnippet());
                        return true;
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
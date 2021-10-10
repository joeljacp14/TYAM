package com.jpdevelopers.myapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PictureActivity extends Activity {
    public static final String TAG = "PicAct";
    public static final int REQUEST_CODE_TAKE_PICTURE = 1004;//se usa para permisos de escritura externa y gps
    String imagePath;
    ImageView viewPicture;
    Bitmap imgBitmap;
    LocationListener locationListener;
    LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate");

        setContentView(R.layout.activity_picture);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");

        viewPicture = findViewById(R.id.picture);
        //tomara un foto, le pondra gps y la guardara en el almacenamiento
        ImageButton btnPhoto = findViewById(R.id.btnNewPhoto);
        btnPhoto.setOnClickListener(view -> {

            int permision = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (permision != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
                    requestPermissions(
                            new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA},
                            REQUEST_CODE_TAKE_PICTURE );
                } else {
                    requestPermissions(
                            new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.CAMERA
                            },
                            REQUEST_CODE_TAKE_PICTURE );
                }
                return;
            }

            takePicture();
            beginRequestLocation();
        });
    }

    @SuppressLint("MissingPermission")
    private void beginRequestLocation () {
        locationListener = new MyLocationListener ();
        locationManager = (LocationManager) getSystemService (LOCATION_SERVICE);
        locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER,
                5000,
                10,
                locationListener);
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            /** CON FILE PROVIDER*/
//            File imagen = null;
//            try {
//                imagen = creaImagen();
//            }catch (IOException e){
//                Log.e("ERROR: ", e.toString());
//            }
//            if (imagen != null){
//                Uri fotoUri = FileProvider.getUriForFile(this, "com.jpdevelopers.myapp", imagen);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
//                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
//            }

            /**CON MEDIASTORE*/
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            //para guardar la imagen
            OutputStream fos = null;
            File imagen = null;
            String nombre;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();

                nombre = "IMG_"+System.currentTimeMillis();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, nombre);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);

                Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                Uri fotoUri = resolver.insert(collection, values);

                try {
                    fos = resolver.openOutputStream(fotoUri);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(fotoUri, values, null, null);
            }else {
                imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                nombre = "IMG_"+System.currentTimeMillis()+".jpg";
                imagen = new File(imagePath, nombre);
                try {
                    fos = new FileOutputStream(imagen);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }

            boolean guardado = imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if (guardado)
                Toast.makeText(this, "Imagen guardada en la galeria!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "La imagen no se pudo guardar :(", Toast.LENGTH_LONG).show();

            if (fos != null){
                try {
                    fos.flush();
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if (imagen != null)//API < 29
                MediaScannerConnection.scanFile(this, new String[]{imagen.toString()}, null, null);

        }
    }

    private File creaImagen() throws IOException {
        String imgTime = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String nombre = "IMG_TEST_"+imgTime;

        //ContentValues values = new ContentValues();
        //values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, nombre);
        //values.put(MediaStore.Images.ImageColumns.LATITUDE, locationListener.onLocationChanged());
        //values.put(MediaStore.Images.ImageColumns.LONGITUDE);

        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File temp = File.createTempFile(nombre, ".jpg", path);
        imagePath = temp.getAbsolutePath();

        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && requestCode == RESULT_OK){
            if (data != null){
                imgBitmap = (Bitmap) data.getExtras().get("data");
                viewPicture.setImageBitmap(imgBitmap);
            }
            //imgBitmap = BitmapFactory.decodeFile(imagePath);
            //viewPicture.setImageBitmap(imgBitmap);
            //viewPicture.setImageURI(Uri.parse(imagePath));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && requestCode == REQUEST_CODE_TAKE_PICTURE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePicture();
            }else {
                Toast.makeText(this, "No se puede capturar la imagen debido a falta de permisos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "OnPause");

        locationManager.removeUpdates (locationListener);
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            Toast.makeText (getBaseContext (),
                    "LocatinChanged: Lat " + location.getLatitude() + " Log: " + location.getLongitude (),
                    Toast.LENGTH_LONG).show ();

            Geocoder geocoder = new Geocoder (getBaseContext(), Locale.getDefault ());
            List<Address> addresses;
            String city = "";

            try {
                addresses = geocoder.getFromLocation (location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size () > 0) {
                    //Log.i ("GEO", addresses.get (0).getLocality ());
                    city = addresses.get (0).getLocality ();
                }
            } catch (IOException ex) {
                ex.printStackTrace ();
            }

            Log.i ("GEO", "City: " + city);
        }

//        @Override
//        public void onLocationChanged(@NonNull List<Location> locations) {
//
//        }
//
//        @Override
//        public void onFlushComplete(int requestCode) {
//
//        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
}

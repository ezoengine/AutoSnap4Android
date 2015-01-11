package ezo.automaticphoto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AutomaticPhotoActivity2 extends Activity {
    private Camera camera; // camera object
    private TextView textTimeLeft; // time left field

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*
        textTimeLeft=(TextView)findViewById(R.id.textTimeLeft); // make time left object
        try {
            camera = Camera.open();
            SurfaceView view = new SurfaceView(this);
            camera.setPreviewDisplay(view.getHolder()); // feed dummy surface to surface
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
        */
    }

    Camera.PictureCallback jpegCallBack=new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            // set file destination and file name
            File destination=new File(Environment.getExternalStorageDirectory(),"myPicture.jpg");
            try {
                Bitmap userImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                // set file out stream
                FileOutputStream out = new FileOutputStream(destination);
                // set compress format quality and stream
                userImage.compress(Bitmap.CompressFormat.JPEG, 90, out);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    };

    public void startTimer(View v){
        new CountDownTimer(5000,1000){
            @Override
            public void onFinish() {
                textTimeLeft.setText("Picture Taken");
                camera.takePicture(null, null, null, jpegCallBack);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                // every time 1 second passes
                textTimeLeft.setText("Seconds Left: "+millisUntilFinished/1000);
            }

        }.start();
    }

}

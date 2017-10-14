package ch.appquest.bict.appquest;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by loris on 13.10.2017.
 */

public class Dechiffrierer extends Fragment{

    View myView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button btn;
    ImageView drawImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.dechiffrierer_layout, container, false);
        btn = (Button) myView.findViewById(R.id.btnCamera);
        drawImg = (ImageView) myView.findViewById(R.id.imgView);
        return myView;
    }

    @Override
    public void onStart() {

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        super.onStart();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            drawImg.setImageBitmap(applyFilter(bm));
        }
    }
    private Bitmap applyFilter(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];

        bitmap.getPixels(data, 0, width, 0, 0, width, height);

        for(int i = 0; i < data.length; i++){
            int R = Color.red(data[i]);
            //int G = Color.green(data[i]);
            //int B = Color.blue(data[i]);
            int A = Color.alpha(data[i]);
            data[i] = (int)( A <<24 | R << 16 | 0 << 8 | 0 );

        }
        // Hier können die Pixel im data-array bearbeitet und
        // anschliessend damit ein neues Bitmap erstellt werden

        return Bitmap.createBitmap(data, width, height, Bitmap.Config.ARGB_8888);
    }
}
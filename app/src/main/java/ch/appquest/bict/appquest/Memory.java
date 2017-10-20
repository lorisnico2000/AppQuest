package ch.appquest.bict.appquest;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;

import static android.app.Activity.RESULT_OK;

/**
 * Created by loris on 13.10.2017.
 */

public class Memory extends Fragment{

    View myView;
    Button btn;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.memory_layout, container, false);
        btn = (Button) myView.findViewById(R.id.btnCamera);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                takeQrCodePicture();
            }
        });
        RecyclerView rv = (RecyclerView) myView.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(gridLayoutManager);
        return myView;
    }
    public void takeQrCodePicture() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setCaptureActivity(MyCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.addExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, true);
        integrator.initiateScan();
    }
    public void addView(CardView cv){
        rv.addView(cv);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == IntentIntegrator.REQUEST_CODE
                && resultCode == RESULT_OK) {

            Bundle extras = intent.getExtras();
            String path = extras.getString(
                    Intents.Scan.RESULT_BARCODE_IMAGE_PATH);

            Bitmap bmp = BitmapFactory.decodeFile(path);
            CardView cv = new CardView(getActivity());
            ImageView img = new ImageView(getActivity());
            TextView txt = new TextView(getActivity());
            img.setImageBitmap(bmp);
            cv.addView(img);
            cv.addView(txt);
            addView(cv);

            String code = extras.getString(
                    Intents.Scan.RESULT);
        }
        Log.d("asdf", "asdf");
    }
}

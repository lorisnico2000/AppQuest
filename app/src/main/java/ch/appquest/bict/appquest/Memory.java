package ch.appquest.bict.appquest;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by loris on 13.10.2017.
 */

public class Memory extends Fragment{

    View myView;
    Button btn;
    Button btnSelection;
    LinearLayout layout;
    ArrayList<Integer> selected;
    ArrayList<CardView> cardviews;
    ArrayList<String> codes;
    ArrayList<String[]> json;
    Boolean firstStart = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(firstStart){
            myView = inflater.inflate(R.layout.memory_layout, container, false);
            btn = (Button) myView.findViewById(R.id.btnCamera);
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    takeQrCodePicture();
                }
            });
            btnSelection = (Button) myView.findViewById(R.id.btnSelection);
            btnSelection.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    addSelection();
                }
            });
            layout = (LinearLayout) myView.findViewById(R.id.linearLayout);
            selected = new ArrayList<Integer>();
            cardviews = new ArrayList<CardView>();
            codes = new ArrayList<String>();
            json = new ArrayList<String[]>();

            firstStart = false;
        }

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
    public void addView(Bitmap bm, String c){

        String code = c;
        CardView cv = new CardView(getActivity());
        LinearLayout ll = new LinearLayout(getActivity());
        ImageView img = new ImageView(getActivity());
        TextView txt = new TextView(getActivity());

        txt.setText(code);
        img.setImageBitmap(bm);
        ll.addView(img);
        ll.addView(txt);
        cv.addView(ll);

        final int id = selected.size();
        selected.add(0);
        cardviews.add(cv);
        codes.add(code);

        cv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(selected.get(id) == 1){
                    selected.set(id, 0);
                    cardviews.get(id).setCardBackgroundColor(Color.argb(0, 0, 0,0));
                }else if(testSelection() && selected.get(id) == 0){
                    selected.set(id, 1);
                    cardviews.get(id).setCardBackgroundColor(Color.argb(120, 0, 0,255));
                }

            }
        });

        layout.addView(cv);
    }
    private boolean testSelection(){
        int count = 0;
        for (Integer b : selected){
            if(b == 1) count++;
        }
        if (count > 1){
            return false;
        }
        return true;
    }
    public void addSelection(){
        if(!testSelection()){
            int c = 0;
            String[] s = new String[2];
            for (int i = 0; i < selected.size(); i++){
                if(selected.get(i) == 1){
                    selected.set(i, 2);
                    cardviews.get(i).setCardBackgroundColor(Color.argb(120, 0, 255,0));
                    s[c] = codes.get(i);
                    c++;
                }
            }
            json.add(s);
        }
    }
    public String log(){
        String solution = "{ \"task\": \"Memory\", \"solution\": [";
        for (int i = 0; i <json.size(); i++){
            String temp = "[\"" + json.get(i)[0] + "\",\"" + json.get(i)[1] + "\"]";
            if(i < json.size()-1){
                temp += ", ";
            }
            solution += temp;
        }
        solution += "] }";
        return solution;
    }
}

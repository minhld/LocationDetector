package edu.minhld.locationdetector;

import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.invokeBtn)
    Button invokeBtn;

    @Bind(R.id.infoTxt)
    TextView infoTxt;

    LocationRetriever mLocationRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        infoTxt.setMovementMethod(new ScrollingMovementMethod());

        invokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTxt.setText("");
            }
        });

        mLocationRetriever = new LocationRetriever(this, infoTxt);
    }


}

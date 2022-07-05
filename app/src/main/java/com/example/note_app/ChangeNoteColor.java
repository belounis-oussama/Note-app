package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import yuku.ambilwarna.AmbilWarnaDialog;

public class ChangeNoteColor extends AppCompatActivity {

LinearLayout cutomcolor;
CircleImageView colorchoosed;
TextView textcolor;
RadioButton randcolorraido,customcolorradio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note_color);
        initWidget();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPreferences.getString("notecolor","random").equals("random"))
        {
        randcolorraido.setChecked(true);
        }
        else
        {
            int choosedcolor=Integer.parseInt(sharedPreferences.getString("notecolor","random"));
        customcolorradio.setChecked(true);
        cutomcolor.setVisibility(View.VISIBLE);
        textcolor.setTextColor(choosedcolor);
        colorchoosed.setColorFilter(choosedcolor);
        textcolor.setText(String.format("#%06X", 0xFFFFFF & choosedcolor));


        }

        cutomcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });


    }

    private void openColorPicker() {
        AmbilWarnaDialog colorpicker=new AmbilWarnaDialog(this, R.color.dark_teal, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor=sharedPreferences.edit();
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                textcolor.setTextColor(color);
                colorchoosed.setColorFilter(color);
                textcolor.setText(String.format("#%06X", 0xFFFFFF & color));
                editor.putString("notecolor",String.valueOf(color));
                editor.apply();

            }
        });
        colorpicker.show();
    }



    public void onRadioButtonClicked(View view) {

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.randcolor:
                if (checked)
                    cutomcolor.setVisibility(View.INVISIBLE);
                    editor.putString("notecolor","random");
                    editor.apply();
                    break;
            case R.id.customcolor:
                if (checked)

                    cutomcolor.setVisibility(View.VISIBLE);

                break;
        }
    }



    private void initWidget() {
        cutomcolor=findViewById(R.id.cutomcolorlayour);
        colorchoosed=findViewById(R.id.choosedcolor);
        textcolor=findViewById(R.id.textcolor);
        randcolorraido=findViewById(R.id.randcolor);
        customcolorradio=findViewById(R.id.customcolor);
    }
}
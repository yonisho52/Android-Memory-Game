package com.example.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityDynamicBoard extends AppCompatActivity {

    private ArrayList<Integer> arr_image;
    private ArrayList<ButtonClass> arr_object;
    private int index = 0, oneObjectSelect, secondObjectSelect, count = 0;
    private ImageButton x_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_board);

        arr_image = new ArrayList<>();
        arr_object = new ArrayList<>();
    }

    public void func_button(View view) {
        int id_button;
        ImageButton imageButton = null;

        if (view instanceof ImageButton) {
            imageButton = (ImageButton) view;
            if ((id_button = findIdButton(view.getId())) == -1) {
                arr_object.add(new ButtonClass(arr_image.get(index), view.getId()));

                imageButton.setImageResource(arr_image.get(index));
                index++;
            } else {
                imageButton.setImageResource(arr_object.get(id_button).getId());
            }
        }
        if (count == 0) {
            oneObjectSelect = arr_object.get(findIdButton(view.getId())).getId();
            x_Button = imageButton;
            count++;
        } else {
            secondObjectSelect = arr_object.get(findIdButton(view.getId())).getId();
            count = 0;
            if (oneObjectSelect == secondObjectSelect && x_Button != view) {
                Toast.makeText(this, "yes", Toast.LENGTH_LONG).show();
                view.setVisibility(view.GONE);
                x_Button.setVisibility(view.GONE);
            }
        }
        Timer buttonTimer = new Timer();
        ImageButton finalImageButton = imageButton;
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finalImageButton.setImageResource(R.drawable.imagefound);
                    }
                });
            }
        }, 3000);
    }

    private int findIdButton(int id) {
        int i = 0;
        for (ButtonClass image : arr_object) {
            if (image.getArrId() == id)

                return i;
            i++;
        }
        return -1;
    }

    public void createBoard(int numOfPairs) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.BoardLayout);
        LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.Linear1);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.Linear2);
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.Linear3);
        LinearLayout layout4 = (LinearLayout) findViewById(R.id.Linear4);


        buttonLayout.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.VISIBLE);
        layout4.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.VISIBLE);

        ArrayList<LinearLayout> layoutList = new ArrayList<LinearLayout>();
        layoutList.add(layout1);
        layoutList.add(layout2);
        layoutList.add(layout3);
        layoutList.add(layout4);

        int buttonsPerRow;
        if (numOfPairs == 4)
            buttonsPerRow = 2;
        else if (numOfPairs == 6)
            buttonsPerRow = 3;
        else
            buttonsPerRow = 4;


        for (int j = 0; j < 2; j++) {
            //Change I for different amount of cards
            for (int i = 1; i <= numOfPairs; i++) {
                arr_image.add(getResources().getIdentifier("ex" + i, "drawable", getPackageName()));
            }
        }
        Collections.shuffle(arr_image);
        int buttonId = 0;

        for (LinearLayout currentLayout : layoutList) {
            for (int j = 0; j < buttonsPerRow; j++) {
                ImageButton btnTag = new ImageButton(this);
                LinearLayout.LayoutParams size = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                size.weight = 1;
                btnTag.setLayoutParams(size);
                btnTag.setId(buttonId++);
                btnTag.setImageResource(R.drawable.imagefound);
                btnTag.setScaleType(ImageView.ScaleType.CENTER);
                btnTag.setAdjustViewBounds(true);
                btnTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        func_button(v);
                    }
                });
                //layout1.addView(btnTag);
                currentLayout.addView(btnTag);
            }
        }
    }

    public void easyMode(View view) {
        createBoard(4);
    }

    public void mediumMode(View view) {
        createBoard(6);
    }

    public void hardMode(View view) {
        createBoard(8);
    }
}

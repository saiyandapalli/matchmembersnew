package com.saiyandapalli.matchthemembers;

import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    String[] memberNames = {"Aayush Tyagi", "Abhinav Koppu", "Aditya Yadav", "Ajay Merchia", "Alice Zhao", "Amy Shen", "Anand Chandra", "Andres Medrano", "Angela Dong", "Anika Bagga", "Anmol Parande", "Austin Davis", "Ayush Kumar", "Brandon David", "Candice Ye", "Carol Wang", "Cody Hsieh", "Daniel Andrews", "Daniel Jing", "Eric Kong", "Ethan Wong", "Fang Shuo", "Izzie Lau", "Jaiveer Singh", "Japjot Singh", "Jeffrey Zhang", "Joey Hejna", "Julie Deng", "Justin Kim", "Kaden Dippe", "Kanyes Thaker", "Kayli Jiang", "Kiana Go", "Leon Kwak", "Levi Walsh", "Louie Mcconnell", "Max Miranda", "Michelle Mao", "Mohit Katyal", "Mudabbir Khan", "Natasha Wong", "Nikhar Arora", "Noah Pepper", "Radhika Dhomse", "Sai Yandapalli", "Saman Virai", "Sarah Tang", "Sharie Wang", "Shiv Kushwah", "Shomil Jain", "Shreya Reddy", "Shubha Jagannatha", "Shubham Gupta", "Srujay Korlakunta", "Stephen Jayakar", "Suyash Gupta", "Tiger Chen", "Vaibhav Gattani", "Victor Sun", "Vidya Ravikumar", "Vineeth Yeevani", "Wilbur Shi", "William Lu", "Will Oakley", "Xin Yi Chen", "Young Lin"};
    ArrayList<String> memberList = new ArrayList<>(Arrays.asList(memberNames));
    int currindex = 0;
    int score = 0;
    Button button1, button2, button3, button4, exitButton;
    TextView scoreView,timerView;
    ImageView imageView;
    CountDownTimer timer;
    static Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Collections.shuffle(memberList);
        exitButton = findViewById(R.id.exitButton);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        imageView = findViewById(R.id.imageView);
        scoreView = findViewById(R.id.scoreView);
        timerView = findViewById(R.id.timerView);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        imageView.setOnClickListener(this);
        scoreView.setText(String.format("%d", score));
        timer = new CountDownTimer(5999, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(GameActivity.this, "Time's up lol", Toast.LENGTH_SHORT).show();
                currindex = currindex + 1;
                onButtonClick();
            }
        };
        onButtonClick();
    }

    static <String> String getRandomItem(List<String> list) {
        return list.get(rand.nextInt(list.size()));
    }


    public void onButtonClick() {
        if (currindex > memberNames.length) {
            currindex = 0;
            Collections.shuffle(memberList);
            score = 0;
            scoreView.setText(String.format("%d", score));
            onButtonClick();
        }

//        display photo

        ArrayList<String> otherNames = new ArrayList<String>();
        while (otherNames.size() < 3) {
            String wrongName = getRandomItem(memberList);
            if(memberList.indexOf(wrongName)!= currindex && !otherNames.contains(wrongName)){
                otherNames.add(wrongName);
            }
        }
        otherNames.add(memberList.get(currindex));
        Collections.shuffle(otherNames);
        button1.setText(otherNames.get(0));
        button2.setText(otherNames.get(1));
        button3.setText(otherNames.get(2));
        button4.setText(otherNames.get(3));

        String personLower = memberList.get(currindex).toLowerCase().replace(" ", "");
        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(personLower, "drawable", getPackageName());
        imageView.setImageDrawable(resources.getDrawable(resourceId));
        timer.cancel();
        timer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
                if(((Button) v).getText().toString().equals(memberList.get(currindex))){
                    score = score +1;
                    Toast.makeText(this, "you're right!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "you're wrong gg.", Toast.LENGTH_SHORT).show();
                }
                currindex = currindex + 1;
                scoreView.setText(String.format("%d", score));
                onButtonClick();
                // if text in v is the same as memberList.get(currIndex) hes correct
                break;
            case R.id.exitButton:
                finish();
                break;
            case R.id.imageView:
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, 420);
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, memberList.get(currindex));
                startActivity(intent);
                break;
        }
    }
}




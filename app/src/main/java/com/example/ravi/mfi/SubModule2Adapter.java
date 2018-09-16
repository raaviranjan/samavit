package com.example.ravi.mfi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ravi on 04-Jul-18.
 */

public class SubModule2Adapter extends RecyclerView.Adapter<SubModule2Adapter.MyViewHolder>  {

    private List<SubModule2Blocks> moviesList;
    private Context context;
    private SharedPreferences preferences;
    private int viewed[][] = new int[7][60];
    private int offset = 0, questionNo = 0, whichLang;
    private String groupNameAndID,preferenceString,language;
    private String questionLIST[];
    private String answer[];
    private String beforeOptionA[],beforeOptionB[],beforeOptionC[],beforeOptionD[];

    public SubModule2Adapter(Context context,List<SubModule2Blocks> moviesList,String questionList[],
                             String beforeOptionA[],String beforeOptionB[],String beforeOptionC[],String beforeOptionD[],String answer[]) {
        this.moviesList = moviesList;
        this.context=context;
        this.questionLIST=questionList;
        this.answer = answer;
        this.beforeOptionA = beforeOptionA;
        this.beforeOptionB = beforeOptionB;
        this.beforeOptionC = beforeOptionC;
        this.beforeOptionD = beforeOptionD;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView,tvDuration,tvSource;
        private ImageView imageView;
        private RelativeLayout relativeLayout;

        private MyViewHolder(View row){

            super(row);
            textView = row.findViewById(R.id.tvFamily1);
            tvDuration = row.findViewById(R.id.tvDuration);
            tvSource = row.findViewById(R.id.tvSource);
            imageView = row.findViewById(R.id.ivFamily1);
            relativeLayout = row.findViewById(R.id.rlFamily1);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.submodule_sample, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final SubModule2Blocks mb = moviesList.get(position);

        preferences = context.getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        groupNameAndID = preferences.getString("group_name",null) + preferences.getString("group_id",null);
        language = preferences.getString("language", "en");

        if(preferences.getString("clicked",null).equals("User"))
            preferenceString = "views";
        else if(preferences.getString("clicked",null).equals("Group"))
            preferenceString = groupNameAndID;

        viewed = getFromPref(preferenceString);

        //this switch case is for percentage modification
        //viewed[whichLang][offset+position]
        //for example: if the second video of first module of compulsory section of english language is viewed
        //then we will set viewed[0][0+1]=1 as whichlang is 0 for english. offset is 0 for firstmodule of compulsory and position is 1 for second video
        switch (language) {
            case "en":
                whichLang = 0;
                if(mb.getModuleName().contains("m1EngComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2EngComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3EngComp"))
                    offset=6;
                else if(mb.getModuleName().contains("m4EngComp"))
                    offset=9;
                else if(mb.getModuleName().contains("m1EngDesi"))
                    offset=14;
                else if(mb.getModuleName().contains("m2EngDesi"))
                    offset=17;
                else if(mb.getModuleName().contains("m3EngDesi"))
                    offset=18;
                else if(mb.getModuleName().contains("m4EngDesi"))
                    offset=20;
                else if(mb.getModuleName().contains("m1EngSupp"))
                    offset=22;
                else if(mb.getModuleName().contains("m2EngSupp"))
                    offset=24;
                else if(mb.getModuleName().contains("m3EngSupp"))
                    offset=28;
                else if(mb.getModuleName().contains("m4EngSupp"))
                    offset=30;
                else if(mb.getModuleName().contains("m5EngSupp"))
                    offset=33;
                else if(mb.getModuleName().contains("m6EngSupp"))
                    offset=35;
                else if(mb.getModuleName().contains("m7EngSupp"))
                    offset=37;
                break;
            case "hi":
                whichLang = 1;
                if(mb.getModuleName().contains("m1HinComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2HinComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3HinComp"))
                    offset=5;
                else if(mb.getModuleName().contains("m4HinComp"))
                    offset=8;
                else if(mb.getModuleName().contains("m1HinDesi"))
                    offset=15;
                else if(mb.getModuleName().contains("m2HinDesi"))
                    offset=17;
                else if(mb.getModuleName().contains("m3HinDesi"))
                    offset=18;
                else if(mb.getModuleName().contains("m4HinDesi"))
                    offset=20;
                else if(mb.getModuleName().contains("m1HinSupp"))
                    offset=22;
                else if(mb.getModuleName().contains("m2HinSupp"))
                    offset=26;
                else if(mb.getModuleName().contains("m3HinSupp"))
                    offset=29;
                else if(mb.getModuleName().contains("m4HinSupp"))
                    offset=34;
                else if(mb.getModuleName().contains("m5HinSupp"))
                    offset=40;
                else if(mb.getModuleName().contains("m6HinSupp"))
                    offset=42;
                else if(mb.getModuleName().contains("m7HinSupp"))
                    offset=46;
                break;
            case "gu":
                whichLang = 2;
                if(mb.getModuleName().contains("m1GujComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2GujComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3GujComp"))
                    offset=5;
                else if(mb.getModuleName().contains("m4GujComp"))
                    offset=8;
                else if(mb.getModuleName().contains("m1GujDesi"))
                    offset=16;
                else if(mb.getModuleName().contains("m2GujDesi"))
                    offset=19;
                else if(mb.getModuleName().contains("m3GujDesi"))
                    offset=20;
                else if(mb.getModuleName().contains("m4GujDesi"))
                    offset=22;
                else if(mb.getModuleName().contains("m1GujSupp"))
                    offset=24;
                else if(mb.getModuleName().contains("m2GujSupp"))//no  video  for m3GujSupp, m5GujSupp
                    offset=26;
                else if(mb.getModuleName().contains("m4GujSupp"))
                    offset=27;
                else if(mb.getModuleName().contains("m6GujSupp"))
                    offset=31;
                else if(mb.getModuleName().contains("m7GujSupp"))
                    offset=33;
                break;
            case "mr":
                whichLang = 3;
                if(mb.getModuleName().contains("m1MarComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2MarComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3MarComp"))
                    offset=5;
                else if(mb.getModuleName().contains("m4MarComp"))
                    offset=8;
                else if(mb.getModuleName().contains("m1MarDesi"))
                    offset=15;
                else if(mb.getModuleName().contains("m2MarDesi"))
                    offset=18;
                else if(mb.getModuleName().contains("m3MarDesi"))
                    offset=19;
                else if(mb.getModuleName().contains("m4MarDesi"))
                    offset=21;
                else if(mb.getModuleName().contains("m1MarSupp"))
                    offset=23;
                else if(mb.getModuleName().contains("m2MarSupp"))
                    offset=25;
                else if(mb.getModuleName().contains("m3MarSupp"))
                    offset=28;
                else if(mb.getModuleName().contains("m4MarSupp"))//no  video  for m5MarSupp
                    offset=29;
                else if(mb.getModuleName().contains("m6MarSupp"))
                    offset=37;
                else if(mb.getModuleName().contains("m7MarSupp"))
                    offset=38;
                break;
            case "ta":
                whichLang = 4;
                if(mb.getModuleName().contains("m1TamComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2TamComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3TamComp"))
                    offset=5;
                else if(mb.getModuleName().contains("m4TamComp"))
                    offset=8;
                else if(mb.getModuleName().contains("m1TamDesi"))
                    offset=15;
                else if(mb.getModuleName().contains("m2TamDesi"))
                    offset=18;
                else if(mb.getModuleName().contains("m3TamDesi"))
                    offset=19;
                else if(mb.getModuleName().contains("m4TamDesi"))
                    offset=21;
                else if(mb.getModuleName().contains("m1TamSupp"))
                    offset=23;
                else if(mb.getModuleName().contains("m2TamSupp"))//no  video for  m3TamSupp
                    offset=27;
                else if(mb.getModuleName().contains("m4TamSupp"))
                    offset=30;
                else if(mb.getModuleName().contains("m5TamSupp"))
                    offset=36;
                else if(mb.getModuleName().contains("m6TamSupp"))
                    offset=37;
                else if(mb.getModuleName().contains("m7TamSupp"))
                    offset=39;
                break;
            case "kn":
                whichLang = 5;
                if(mb.getModuleName().contains("m1KanComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2KanComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3KanComp"))
                    offset=5;
                else if(mb.getModuleName().contains("m4KanComp"))
                    offset=8;
                else if(mb.getModuleName().contains("m1KanDesi"))
                    offset=15;
                else if(mb.getModuleName().contains("m2KanDesi"))
                    offset=18;
                else if(mb.getModuleName().contains("m3KanDesi"))
                    offset=19;
                else if(mb.getModuleName().contains("m4KanDesi"))
                    offset=21;
                else if(mb.getModuleName().contains("m1KanSupp"))
                    offset=23;
                else if(mb.getModuleName().contains("m2KanSupp"))//no video for m3KanSupp,m5KanSupp,m6KanSupp
                    offset=25;
                else if(mb.getModuleName().contains("m4KanSupp"))
                    offset=27;
                else if(mb.getModuleName().contains("m7KanSupp"))
                    offset=35;
                break;
            case "or":
                whichLang = 6;
                if(mb.getModuleName().contains("m1OriComp"))
                    offset=0;
                else if(mb.getModuleName().contains("m2OriComp"))
                    offset=3;
                else if(mb.getModuleName().contains("m3OriComp"))
                    offset=5;
                else if(mb.getModuleName().contains("m4OriComp"))
                    offset=8;
                else if(mb.getModuleName().contains("m1OriDesi"))
                    offset=15;
                else if(mb.getModuleName().contains("m2OriDesi"))
                    offset=18;
                else if(mb.getModuleName().contains("m3OriDesi"))
                    offset=19;
                else if(mb.getModuleName().contains("m4OriDesi"))
                    offset=21;
                else if(mb.getModuleName().contains("m1OriSupp"))//no video for m2OriSupp,m3OriSupp,m5OriSupp
                    offset=23;
                else if(mb.getModuleName().contains("m4OriSupp"))
                    offset=24;
                else if(mb.getModuleName().contains("m6OriSupp"))
                    offset=31;
                else if(mb.getModuleName().contains("m7OriSupp"))
                    offset=32;
                break;
        }

        holder.textView.setText(mb.getTitle());
        holder.tvDuration.setText(mb.getDuration());
        holder.tvSource.setText(context.getResources().getString(R.string.video_source)+mb.getSource());
        holder.imageView.setImageResource(mb.getImageUrl());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(mb.getModuleName().contains("Eng")){
                    switch (mb.getTitle()) {
                        case "Borrowing"://compulsory video starts here
                            questionNo = 10;
                            break;
                        case "Credit Literacy":
                            questionNo = 12;
                            break;
                        case "Grievance Redressal –For Loan Client":
                            questionNo = 34;
                            break;
                        case "Importance of Savings":
                            questionNo = 6;
                            break;
                        case "Savings in Bank":
                            questionNo = 8;
                            break;
                        case "Types of Insurance":
                            questionNo = 14;
                            break;
                        case "ATM":
                            questionNo = 20;
                            break;
                        case "ATM Debit Card - Fraud and Prevention":
                            questionNo = 32;
                            break;
                        case "USSD (*99#)":
                            questionNo = 24;
                            break;
                        case "BHIM App":
                            questionNo = 26;
                            break;
                        case "Aadhaar Enabled Payment System (AEPS)"://compulsory video ends here
                            questionNo = 22;
                            break;
                        case "Financial Planning"://desirable video starts here
                            questionNo = 0;
                            break;
                        case "Budgeting":
                            questionNo = 2;
                            break;
                        case "Needs and Wants":
                            questionNo = 4;
                            break;
                        case "Atal Pension Yojana":
                            questionNo = 18;
                            break;
                        case "Security Features of Rs.100":
                            questionNo = 36;
                            break;
                        case "Security Features of Rs.500 and Rs.2000"://desirable video ends here
                            questionNo = 38;
                            break;
                        default:
                            questionNo = -1;
                            break;
                    }
                } else if(mb.getModuleName().contains("Hin")){
                    switch (mb.getTitle()) {
                        case "ऋण माफ़ी अफवाह"://compulsory video starts here
                            questionNo = 8;
                            break;
                        case "ऋण साक्षरता":
                            questionNo = 10;
                            break;
                        case "ऋण शिकायत निवारण":
                            questionNo = 32;
                            break;
                        case "प्रधानमंत्री जन धन योजना":
                            questionNo = 4;
                            break;
                        case "बुनियादी बचत बैंक जमा खाता (BSBDA)":
                            questionNo = 6;
                            break;
                        case "प्रधानमंत्री जीवन ज्योति बीमा योजना":
                            questionNo = 12;
                            break;
                        case "प्रधानमंत्री जीवन जन सुरक्षा बीमा योजना":
                            questionNo = 14;
                            break;
                        case "बीमा शिकायत निवारण":
                            questionNo = 34;
                            break;
                        case "एटीएम (ATM)":
                            questionNo = 20;
                            break;
                        case "एटीएम डेबिट कार्ड - धोखाधड़ी और निवारण":
                            questionNo = 36;
                            break;
                        case "फ़ोन द्वारा धोखाधड़ी":
                            questionNo = 42;
                            break;
                        case "माइक्रो एटीएम":
                            questionNo = 28;
                            break;
                        case "आधार सक्षम भुगतान प्रणाली (AEPS)":
                            questionNo = 22;
                            break;
                        case "यूएसएसडी (*99#)":
                            questionNo = 24;
                            break;
                        case "भीम (BHIM) एप"://compulsory video ends here
                            questionNo = 26;
                            break;
                        case "वित्तीय योजना और लक्ष्य"://desirable video starts here
                            questionNo = 0;
                            break;
                        case "वित्तीय योजना":
                            questionNo = 2;
                            break;
                        case "अटल पेंशन योजना":
                            questionNo = 16;
                            break;
                        case "डिजिटल धोखाधड़ी होने पे क्या करें":
                            questionNo = 38;
                            break;
                        case "ईमेल(Email) द्वारा धोखाधड़ी":
                            questionNo = 40;
                            break;
                        case "100 रुपये की सुरक्षा विशेषताएं":
                            questionNo = 44;
                            break;
                        case "500 रुपये और 2000 रुपये की सुरक्षा विशेषताएं"://desirable video ends here
                            questionNo = 46;
                            break;
                        default:
                            questionNo = -1;
                            break;
                    }
                }
                boolean checkQuizToAppearOnce;
                checkQuizToAppearOnce = preferences.getBoolean("quiz_done_" + mb.getModuleName()+position,false);
                //remove if block from below if-else, as there is no quiz for these languages
                if(mb.getModuleName().contains("Guj") || mb.getModuleName().contains("Mar") || mb.getModuleName().contains("Tam")
                        || mb.getModuleName().contains("Kan")|| mb.getModuleName().contains("Ori")){
                    if(mb.getUrl().contains("---")){
                        Toast.makeText(context, "No Video", Toast.LENGTH_SHORT).show();
                    } else {
                        //storing no of videos viewed of particular module
                        viewed[whichLang][position+offset] = 1;
                        storeInPref(preferenceString, viewed);
                        //opening youtube video
                        // Toast.makeText(context, mb.getUrl(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mb.getUrl()));
                        intent.putExtra("force_fullscreen", true);
                        context.startActivity(intent);
                    }
                } else {
                    //there is no video
                    if(mb.getUrl().contains("---")){
                        Toast.makeText(context, "No Video", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!checkQuizToAppearOnce){
                            if(questionNo>=0){
                                //creating message box before quiz
                                final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(context);
                                LayoutInflater inflater1 = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                View dialogView1 = inflater1.inflate(R.layout.proceed_alert, null);
                                dialogBuilder1.setView(dialogView1);

                                Button cancel = dialogView1.findViewById(R.id.cancelButton);
                                Button proceed = dialogView1.findViewById(R.id.proceedButton);

                                final AlertDialog alert2 = dialogBuilder1.create();
                                alert2.show();

                                proceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alert2.dismiss();
                                        quizAlert(questionNo,mb,0,position,whichLang);
                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alert2.dismiss();
                                    }
                                });
                            } else {//there is no quiz for this
                                //Making flag false so that after quiz will not be asked again
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("flag",false);
                                editor.apply();
                                //storing no of videos viewed of particular module
                                viewed[whichLang][position+offset] = 1;
                                storeInPref(preferenceString, viewed);
                                //opening youtube video
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mb.getUrl()));
                                intent.putExtra("force_fullscreen", true);
                                context.startActivity(intent);
                            }
                        }
                        else {//quiz has been already attempted
                            //Making flag false so that after quiz will not be asked again
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("flag",false);
                            editor.apply();
                            //opening youtube video
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mb.getUrl()));
                            intent.putExtra("force_fullscreen", true);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });
    }
    public void  storeInPref(String name, int[][] array) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count_" + name, 60);
        for(int jj = 0; jj < 7; jj++){
            for (int j = 0; j < 60; j++) {
                editor.putInt("IntValue_" + name +jj+ j, array[jj][j]);
            }
        }
        editor.apply();
    }
    public int[][] getFromPref(String name) {
        int ret[][];
        int count = preferences.getInt("count_" + name, 60);
        ret = new int[7][count];
        for(int kk = 0; kk < 7; kk++){
            for (int k = 0; k < count; k++) {
                ret[kk][k] = preferences.getInt("IntValue_" + name +kk+ k, 10);
            }
        }
        return ret;
    }

    public void quizAlert(int a, final SubModule2Blocks mb, int flag, final int position, final int whichLang){
        flag++;
        final String question,correctAns;
        final String[] option = new String[4];

        question = questionLIST[a];
        option[0] = beforeOptionA[a];
        option[1] = beforeOptionB[a];
        option[2] = beforeOptionC[a];
        option[3] = beforeOptionD[a];
        correctAns = answer[a];

        List<String> arrayList = new ArrayList<>();
        for(int k = 0;k<4;k++){
            if(!option[k].equals("")){
                arrayList.add(option[k]);//if option is not null then adding it to the list
            }
        }

        //creating alertDialog box for "before quiz" questions
        final AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);
        TextView textView = new TextView(context);
        textView.setText(question);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(60,30,50,0);
        builder.setCustomTitle(textView);
        builder.setSingleChoiceItems(arrayList.toArray(new String[arrayList.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selectedIndex) {

            }
        });
        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();

        final int finalFlag = flag;
        //putting some bottom margin for button
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0,0,0,10);
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setLayoutParams(params);

        //converting button text from "Next" to "Done"
        if(flag == 2)
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setText(R.string.doneButton);

        //positive button OnClick
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = false;
                int selectedPosition = alert.getListView().getCheckedItemPosition();
                if(selectedPosition>=0)
                {
                    if(option[selectedPosition].equals(correctAns)){
                        //Do something when answer is correct
                        //Toast.makeText(context, R.string.correct_answer, Toast.LENGTH_SHORT).show();
                    } else {
                        //Do something when answer is incorrect
                        //Toast.makeText(context, context.getString(R.string.ans_should_b)+correctAns+"' !!!", Toast.LENGTH_SHORT).show();
                    }

                    questionNo++;
                    if(finalFlag == 2)
                    {
                        wantToCloseDialog = true;
                        //Toast.makeText(context, mb.getModuleName()+position, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Which_submodule",mb.getTitle());
                        editor.putBoolean("flag",true);
                        editor.putBoolean("quiz_done_" + mb.getModuleName()+position,true);
                        editor.apply();
                        //opening youtube video
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mb.getUrl()));
                        intent.putExtra("force_fullscreen", true);
                        context.startActivity(intent);
                        //storing no of videos viewed of particular module
                        viewed[whichLang][position+offset] = 1;
                        storeInPref(preferenceString, viewed);

                    }
                    else
                    {
                        quizAlert(questionNo,mb, finalFlag,position,whichLang);
                        wantToCloseDialog = true;
                    }
                }
                else
                {
                    Toast.makeText(context, R.string.please_select_an_option, Toast.LENGTH_SHORT).show();
                    wantToCloseDialog = false;
                }
                if(wantToCloseDialog)
                    alert.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}

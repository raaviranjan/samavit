package com.example.ravi.mfi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ravi on 04-Jul-18.
 */

public class SubModule2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubModule2Adapter adapter;
    ArrayList<SubModule2Blocks> blocks ;
    String moduleName,language,clicked,whichSubModule;
    SharedPreferences preferences;
    Boolean flags;
    int questionNo = 0, flag;
    String afterQuestion[];
    String afterOptionA[],afterOptionB[],afterOptionC[],afterOptionD[];
    String afterAnswer[];
    String choice;
    int noOfClicks;

    String urls[][] = new String[7][60];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadLang();
        setContentView(R.layout.submodule_recycler);

    }

    //Onresume starts
    @Override
    protected void onResume() {
        super.onResume();
        loadLang();

        for(int i = 0;i<7;i++){
            for(int j = 0;j<60;j++){
                urls[i][j]="---";
            }
        }

        //to add back button on toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //beforeQuiz
        String beforeQuestion[]=this.getResources().getStringArray(R.array.before_questions);
        String beforeOptionA[] = this.getResources().getStringArray(R.array.before_optionA);
        String beforeOptionB[] = this.getResources().getStringArray(R.array.before_optionB);
        String beforeOptionC[] = this.getResources().getStringArray(R.array.before_optionC);
        String beforeOptionD[] = this.getResources().getStringArray(R.array.before_optionD);
        String beforeAnswer[] = this.getResources().getStringArray(R.array.before_answer);

        //afterQuiz
        afterQuestion = this.getResources().getStringArray(R.array.after_question);
        afterOptionA = this.getResources().getStringArray(R.array.after_optionA);
        afterOptionB = this.getResources().getStringArray(R.array.after_optionB);
        afterOptionC = this.getResources().getStringArray(R.array.after_optionC);
        afterOptionD = this.getResources().getStringArray(R.array.after_optionD);
        afterAnswer = this.getResources().getStringArray(R.array.after_answer);

        //English video links
        //compulsory video links
        urls[0][0] = "https://www.youtube.com/watch?v=-Tet0oNrNjg";//responsible borrowing
        urls[0][1] = "https://www.youtube.com/watch?v=n5epw43AZ0c";
        urls[0][2] ="https://www.youtube.com/watch?v=fPJsDW1rlaA";
        urls[0][3] = "https://www.youtube.com/watch?v=JqYoLQXO7j4";//savings
        urls[0][4] = "https://www.youtube.com/watch?v=5x5c9wwQok8";
        urls[0][5] = "---";
        urls[0][6] = "https://www.youtube.com/watch?v=FVE51Lq77Hg";//insurance
        urls[0][7] = "---";
        urls[0][8] = "https://www.youtube.com/watch?v=mCBkpK__ZeY";
        urls[0][9] = "https://www.youtube.com/watch?v=I_gt61o0C9E";//digital
        urls[0][10] = "https://www.youtube.com/watch?v=HfJknBmI1O0";
        urls[0][11] = "https://www.youtube.com/watch?v=kHP4NY3us3g";
        urls[0][12] = "https://www.youtube.com/watch?v=HSthWET5zaA";
        urls[0][13] = "https://www.youtube.com/watch?v=yQ1Gx994um8";

        //desirable video links
        urls[0][14] = "https://www.youtube.com/watch?v=XYxgkCuR_hs";//financial planning
        urls[0][15] = "https://www.youtube.com/watch?v=FSnyyBMNPo8";
        urls[0][16] = "https://www.youtube.com/watch?v=9ZxpWI1rDTE";
        urls[0][17] = "---";//pension
        urls[0][18] = "https://www.youtube.com/watch?v=HfJknBmI1O0";//consumer beware
        urls[0][19] = "---";
        urls[0][20] = "https://www.youtube.com/watch?v=BErbwGQWREs";//currency literacy
        urls[0][21] = "https://www.youtube.com/watch?v=02ytq-MzpD4";

        //supplementary video links
        urls[0][22] = "https://www.youtube.com/watch?v=xXDM7DgJbyw";//loan
        urls[0][23] = "https://www.youtube.com/watch?v=a2f0ZdXBp4g";
        urls[0][24] = "https://www.youtube.com/watch?v=2XZ-n82fUoE";//savings
        urls[0][25] = "https://www.youtube.com/watch?v=BV-bchCAUQk";
        urls[0][26] = "https://www.youtube.com/watch?v=zNiSlvIxw6s";
        urls[0][27] = "https://www.youtube.com/watch?v=Ha9jbZPHN0U";
        urls[0][28] = "https://www.youtube.com/watch?v=kYyPOzK1LNc";//insurance(3.2 and 3.3)
        urls[0][29] = "https://www.youtube.com/watch?v=4ju6lG98WUc";
        urls[0][30] = "https://www.youtube.com/watch?v=T7q6ax9DIDk";//digital
        urls[0][31] = "https://www.youtube.com/watch?v=hocAw8ePMSQ";
        urls[0][32] = "https://www.youtube.com/watch?v=EhiaHWwQasw";
        urls[0][33] = "https://www.youtube.com/watch?v=t4eBjt8C5VA";//financial planning
        urls[0][34] = "https://www.youtube.com/watch?v=XYxgkCuR_hs";
        urls[0][35] = "https://www.youtube.com/watch?time_continue=9&v=O9KJcNmBGwA";//pension(3.1 and 3.4)
        urls[0][36] = "https://www.youtube.com/watch?v=awgcbbQevss";
        urls[0][37] = "https://www.youtube.com/watch?v=zzIFJBcW1g4";//others
        urls[0][38] = "https://www.youtube.com/watch?v=vLeC6khWzpM";
        urls[0][39] = "https://www.youtube.com/watch?v=_iyucgsiLEM";
        urls[0][40]=  "https://www.youtube.com/watch?v=5aILRjJYxvM";


        //Hindi Video Links
        //compulsory video links
        urls[1][0]= "https://www.youtube.com/watch?v=weoouoMs9pI";//responsible borrowing
        urls[1][1]= "https://www.youtube.com/watch?v=ldFNerSMcAA";
        urls[1][2]= "https://www.youtube.com/watch?v=NrdsJapVIqE";
        urls[1][3]= "https://www.youtube.com/watch?v=6dLcaFeCiFk";//savings
        urls[1][4]= "https://www.youtube.com/watch?v=DXYpWlFESGo";
        urls[1][5]= "https://www.youtube.com/watch?v=X-ZnUj5RpAc";//insurance
        urls[1][6]= "https://www.youtube.com/watch?v=Dj3KDYCA2gQ";
        urls[1][7]= "https://www.youtube.com/watch?v=snwAEN8oOKM";
        urls[1][8]= "https://www.youtube.com/watch?v=9mAT3K45DBE";//digital
        urls[1][9]= "https://www.youtube.com/watch?v=EhiaHWwQasw";
        urls[1][10]= "https://www.youtube.com/watch?v=v3G3DaW_d8w";
        urls[1][11]= "https://www.youtube.com/watch?v=5SVep1WQwkc";
        urls[1][12]= "https://www.youtube.com/watch?v=05md0quCvmM&t=118s";
        urls[1][13]= "https://www.youtube.com/watch?v=u-70AV-60Lg";
        urls[1][14]= "https://www.youtube.com/watch?v=5w_zwvsSu7s";
        //desirable video  links
        urls[1][15]= "http://www.ncfeindia.org/component/contushdvideoshare/player/23/56?Itemid=";//financial planning
        urls[1][16]= "https://www.youtube.com/watch?v=yXQHiFyQC8w";
        urls[1][17]= "https://www.youtube.com/watch?v=mhZFiWCgFvw";//pension
        urls[1][18]= "https://www.youtube.com/watch?v=3XtvBgWyCCI";//consumer beware
        urls[1][19]= "https://www.youtube.com/watch?v=YSrjfGS1_ng";
        urls[1][20]= "https://www.youtube.com/watch?v=BErbwGQWREs";//currency literacy
        urls[1][21]= "https://www.youtube.com/watch?v=02ytq-MzpD4";
        //supplementary video links
        urls[1][22]= "https://www.youtube.com/watch?v=saPvjgl2WKM";//loan
        urls[1][23]= "https://www.youtube.com/watch?v=nJWdI5bNvuc";
        urls[1][24]= "https://www.youtube.com/watch?v=2gV7QWdUdzk";
        urls[1][25]= "https://www.youtube.com/watch?v=HkmCvzDX8O8";
        urls[1][26]= "https://www.youtube.com/watch?v=7m3OOoXL4eQ";//savings
        urls[1][27]= "https://www.youtube.com/watch?v=HmP9XpJ94F0";
        urls[1][28]= "https://www.youtube.com/watch?v=UjktXdV_KYE";
        urls[1][29]= "https://www.youtube.com/watch?v=G-dbuDRIz9M";//insurance
        urls[1][30]= "https://www.youtube.com/watch?v=5xp-AW5vICQ";
        urls[1][31]= "https://www.youtube.com/watch?v=Dj3KDYCA2gQ";
        urls[1][32]= "https://www.youtube.com/watch?v=hJWxyR00Rlk";
        urls[1][33]= "https://www.youtube.com/watch?v=ytBv97rNTkc";
        urls[1][34]= "https://www.youtube.com/watch?time_continue=5&v=tNLIN701ub8";//digital
        urls[1][35]= "http://hindi.ncfeindia.org/component/contushdvideoshare/player/19/89";
        urls[1][36]= "https://www.youtube.com/watch?v=ftVutL7O8uo";
        urls[1][37]= "https://www.youtube.com/watch?v=2mo6kflCFyw";
        urls[1][38]= "https://www.youtube.com/watch?v=KR3t8Zfe02k";
        urls[1][39]= "https://www.youtube.com/watch?v=gRUGl42lMzM";
        urls[1][40]= "https://www.youtube.com/watch?v=gXWlnegrZfk";//financial
        urls[1][41]= "https://www.youtube.com/watch?time_continue=3&v=f-AZ-LAc3-8";
        urls[1][42]= "https://www.youtube.com/watch?v=rZX_HAv5Cvw";//pension
        urls[1][43]= "https://www.youtube.com/watch?v=9bhv_2NpBa8";
        urls[1][44]= "https://www.youtube.com/watch?v=qdj4B8Dx8m4";
        urls[1][45]= "https://www.youtube.com/watch?v=HK9PrV3Fo0s";
        urls[1][46]= "https://www.youtube.com/watch?v=tgDv-KxMuO4";//others
        urls[1][47]= "https://www.youtube.com/watch?v=TdsCFOdPFEY&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=42";
        urls[1][48]= "https://www.youtube.com/watch?v=_uKNBZxMVcE&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=35";
        urls[1][49]= "https://www.youtube.com/watch?v=I9KZcRdRZsQ";
        urls[1][50]= "https://www.youtube.com/watch?v=Jgx0j2WuQ_g&list=PLahlISVNeN2a9TUmkOxWLwwfq-6zuz1Dq";
        urls[1][51]= "https://www.youtube.com/watch?v=z1ZUcTLtKT4";
        urls[1][52]= "https://www.youtube.com/watch?v=8TqYFfcGmDY";
        urls[1][53]= "https://www.youtube.com/watch?v=3YA1NFiMQjI";
        urls[1][54]= "https://www.youtube.com/watch?v=pXCpi_CmF8A";
        urls[1][55]= "https://www.youtube.com/watch?v=b5vlf6I2Q1Y";

        //Gujarati Video Links
        //compulsory video links
        urls[2][0] = "https://www.youtube.com/watch?v=JX0FmHMvkzM";//responsible borrowing
        urls[2][1] = "---";
        urls[2][2] = "---";
        urls[2][3] = "https://www.youtube.com/watch?v=3fUvjG9xzSM";//savings
        urls[2][4] = "---";
        urls[2][5] = "---";//insurance
        urls[2][6] = "---";
        urls[2][7] = "---";
        urls[2][8] = "---";//digital
        urls[2][9] = "https://www.youtube.com/watch?v=bC-L1WjNZPw";
        urls[2][10] = "---";
        urls[2][11] = "https://www.youtube.com/watch?v=Wg6J1LNq_5Y";
        urls[2][12] = "https://www.youtube.com/watch?v=5qiPnT-z06Q";
        urls[2][13] = "http://gujarati.ncfeindia.org/component/contushdvideoshare/player/41/247?Itemid=";
        urls[2][14] = "http://gujarati.ncfeindia.org/component/contushdvideoshare/player/41/246?Itemid=";
        urls[2][15] = "https://www.youtube.com/watch?v=uiw8YLVnN8k";
        //desirable video links
        urls[2][16] = "---";//financial planning
        urls[2][17] = "---";
        urls[2][18] = "---";
        urls[2][19] = "https://www.youtube.com/watch?v=dqTJ9o3Uii4";//pension
        urls[2][20] = "---";//consumer beware
        urls[2][21] = "https://www.youtube.com/watch?v=ny_RThQTg-k&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=41";
        urls[2][22] = "---";//currency literacy
        urls[2][23] = "https://www.youtube.com/watch?v=wJB29ljyQ-w";
        //supplementary video links
        urls[2][24] = "https://www.youtube.com/watch?v=SIfixF9HrS4";//loan
        urls[2][25] = "https://www.youtube.com/watch?v=i7q0hSqharw";
        urls[2][26] = "https://www.youtube.com/watch?v=r55NgOwNFTU";//savings
        urls[2][27] = "https://www.youtube.com/watch?v=ULZlk0op7J8&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=36";//digital
        urls[2][28] = "https://www.youtube.com/watch?v=AWiqXQ9hQOI";
        urls[2][29] = "https://www.youtube.com/watch?v=GsGVNWdf2cA";
        urls[2][30] = "https://www.youtube.com/watch?v=8veuF3Xmo4I";
        urls[2][31] = "https://www.youtube.com/watch?v=4fgNGQQGvM0";//pension
        urls[2][32] = "http://gujarati.ncfeindia.org/component/contushdvideoshare/player/42/251?Itemid=";
        urls[2][33] = "https://www.youtube.com/watch?v=wQ34auDlaFU";//others
        urls[2][34] = "https://www.youtube.com/watch?v=UyKCOQkC0FM";
        urls[2][35] ="https://www.youtube.com/watch?v=TKqjjytWQwM&index=33&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";

        //Marathi Video Links
        //compulsory video links
        urls[3][0]= "https://www.youtube.com/watch?v=Rax7X04VWcQ";//responsible borrowing
        urls[3][1]= "---";
        urls[3][2]= "---";
        urls[3][3]= "---";//savings
        urls[3][4]= "---";
        urls[3][5]= "---";//insurance
        urls[3][6]= "---";
        urls[3][7]= "---";
        urls[3][8]= "https://www.youtube.com/watch?v=pXOo5IKsjH0";//digital
        urls[3][9]= "---";
        urls[3][10]= "---";
        urls[3][11]= "https://www.youtube.com/watch?v=MtJSOjTV1dU";
        urls[3][12]= "https://www.youtube.com/watch?v=IL98pe8WEsE";
        urls[3][13]= "http://marathi.ncfeindia.org/component/contushdvideoshare/player/41/245?Itemid=";
        urls[3][14]= "---";
        //desirable video links
        urls[3][15]= "https://www.youtube.com/watch?v=0j8ngOiKFZI";//financial planning
        urls[3][16]= "---";
        urls[3][17]= "---";
        urls[3][18]= "---";//pension
        urls[3][19]= "---";//consumer beware
        urls[3][20]= "https://www.youtube.com/watch?v=3_NIe2wtVZo&list=PLavNoUnETM3Ab7UQ7mk4681j4EsrLhHeF&index=39";
        urls[3][21]= "---";//currency literacy
        urls[3][22]= "---";
        //supplementary video links
        urls[3][23]= "https://www.youtube.com/watch?v=LXS5v9pe5jM";//loan
        urls[3][24]= "https://www.youtube.com/watch?v=fLYz5VGiQy0";
        urls[3][25]= "https://www.youtube.com/watch?v=DP4KghyKmz8";//savings
        urls[3][26]= "https://www.youtube.com/watch?v=H1ch78rc3XM";
        urls[3][27]= "https://www.youtube.com/watch?v=gKUfstdlq-U";
        urls[3][28]= "https://www.youtube.com/watch?v=cFeTdJAovG0";//insurance
        urls[3][29]= "https://www.youtube.com/watch?v=Js7VdeFeMTc";//digital
        urls[3][30]= "https://www.youtube.com/watch?v=tOcXSawg1yg&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=43";
        urls[3][31]= "https://www.youtube.com/watch?v=AA5fo0mJr-A&index=26&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";
        urls[3][32]= "http://marathi.ncfeindia.org/component/contushdvideoshare/player/41/246?Itemid=";
        urls[3][33]= "https://www.youtube.com/watch?v=zW5zUq1hVCM";
        urls[3][34]= "https://www.youtube.com/watch?v=6F5NwQQ3TxU&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=50";
        urls[3][35]= "https://www.youtube.com/watch?v=w6buaqnHoow&feature=youtu.be&list=PLavNoUnETM3Ab7UQ7mk4681j4EsrLhHeF";
        urls[3][36]= "https://www.youtube.com/watch?v=gZr83W5CvTs&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=57";
        urls[3][37]= "http://marathi.ncfeindia.org/component/contushdvideoshare/player/38/247?Itemid=";//pension
        urls[3][38]= "https://www.youtube.com/watch?v=H_Kzdh_QPB8";//others
        urls[3][39]= "https://www.youtube.com/watch?v=yUh4_38C3L0&index=39&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";

        //Tamil Video Links
        //compulsory video links
        urls[4][0]= "https://www.youtube.com/watch?v=aMozccO_VtQ";//responsible borrowing
        urls[4][1]= "https://www.youtube.com/watch?v=atiYhzXajx0&list=PLs7YOf7fxnTkIQrl_oMv2aBsqy1d5FZaj&index=3";
        urls[4][2]= "https://www.youtube.com/watch?v=y96mvAr07Zo&index=3&list=PLs7YOf7fxnTn44H9V4WU2v30YxoRmo0ar";
        urls[4][3]= "---";//savings
        urls[4][4]= "https://www.youtube.com/watch?v=tezt_1OQ2Bk";
        urls[4][5]= "https://www.youtube.com/watch?v=_sbWSFG5zks";//insurance
        urls[4][6]= "---";
        urls[4][7]= "https://www.youtube.com/watch?v=QIZCRXtd3vc";
        urls[4][8]= "https://www.youtube.com/watch?v=pXOo5IKsjH0";//digital
        urls[4][9]= "---";
        urls[4][10]= "---";
        urls[4][11]= "https://www.youtube.com/watch?v=2Ye5JqIPAwE&index=3&list=PLqa2qnsD-uuAxFkEvS-NlWhDPGEQVG-Ri";
        urls[4][12]= "https://www.youtube.com/watch?v=MDbG4hwdUWE";
        urls[4][13]= "https://www.youtube.com/watch?v=5E8J54yfPbM&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=28";
        urls[4][14]= "---";
        //desirable video links
        urls[4][15]= "https://www.youtube.com/watch?v=KeUxp24M8hY&feature=youtu.be";//financial planning
        urls[4][16]= "---";
        urls[4][17]= "---";
        urls[4][18]= "https://www.youtube.com/watch?v=z5EgnwpAm_Y";//pension
        urls[4][19]= "https://www.youtube.com/watch?v=Fzi8GTb-oOI";//consumer beware
        urls[4][20]= "https://www.youtube.com/watch?v=3_NIe2wtVZo&list=PLavNoUnETM3Ab7UQ7mk4681j4EsrLhHeF&index=39";
        urls[4][21]= "---";//currency literacy
        urls[4][22]= "---";
        //supplementary video links
        urls[4][23]= "https://www.youtube.com/watch?v=NAySi1HERpQ";//loan
        urls[4][24]= "https://www.youtube.com/watch?v=oHnUSNnD80Y";
        urls[4][25]= "https://www.youtube.com/watch?v=fKm6xjYaXXU";
        urls[4][26]= "https://www.youtube.com/watch?v=diY4bnoq8jM";
        urls[4][27]= "https://www.youtube.com/watch?v=NnKK28co13I&feature=youtu.be";//savings
        urls[4][28]= "https://www.youtube.com/watch?v=CkCLDBN-Qmg";
        urls[4][29]= "https://www.youtube.com/watch?v=KETZlpG-CDA";
        urls[4][30]= "https://www.youtube.com/watch?v=5E8J54yfPbM&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=28";//digital
        urls[4][31]= "https://www.youtube.com/watch?v=T0lO0DdRExI";
        urls[4][32]= "https://www.youtube.com/watch?v=o3MkpWqws3Q&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=52";
        urls[4][33]= "https://www.youtube.com/watch?v=5E8J54yfPbM&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=28";
        urls[4][34]= "https://www.youtube.com/watch?v=GpDC7bFubu0&index=16&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";
        urls[4][35]= "https://www.youtube.com/watch?v=NPj4IIoEkRg&index=47&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";
        urls[4][36]= "https://www.youtube.com/watch?v=wvYpsrqUTMY";//financial
        urls[4][37]= "https://www.youtube.com/watch?v=QbaLvusaiPY";//pension
        urls[4][38]= "https://www.youtube.com/watch?v=rtTezXvPNME&index=7&list=PLmhpqJCj6osbpypHqTcn3ROLRoxtBr9pY";
        urls[4][39]= "https://www.youtube.com/watch?v=xDIXvR5eSkA&index=54&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";//others
        urls[4][40]= "https://www.youtube.com/watch?v=YH5too8dB7M";
        urls[4][41]= "https://www.youtube.com/watch?v=bkzZf8DRjb0";

        //Kannada Video Links
        //compulsory video links
        urls[5][0] = "https://www.youtube.com/watch?v=mp9FGmVmj1A";//responsible borrowing
        urls[5][1] = "---";
        urls[5][2] = "---";
        urls[5][3] = "---";//savings
        urls[5][4] = "https://www.youtube.com/watch?v=XEPcVp5zs2A";
        urls[5][5] = "---";//insurance
        urls[5][6] = "---";
        urls[5][7] = "https://www.youtube.com/watch?time_continue=14&v=lX9CBEW29dA";
        urls[5][8] = "---";//digital
        urls[5][9] = "---";
        urls[5][10] = "---";
        urls[5][11] = "https://www.youtube.com/watch?v=eRhKgkoQf7o";
        urls[5][12] = "---";
        urls[5][13] = "https://www.youtube.com/watch?v=op3nX6DOvi4&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=10";
        urls[5][14] = "---";
        //desirable video links
        urls[5][15] = "---";//financial planning
        urls[5][16] = "---";
        urls[5][17] = "---";
        urls[5][18] = "https://www.youtube.com/watch?v=upTePdCpPdQ";//pension
        urls[5][19] = "---";//consumer beware
        urls[5][20] = "---";
        urls[5][21] = "---";//currency literacy
        urls[5][22] = "https://www.youtube.com/watch?v=RZD9z2Y5CTM\n";
        //supplementary video links
        urls[5][23] = "https://www.youtube.com/watch?v=fbggjhPNExU";//loan
        urls[5][24] = "https://www.youtube.com/watch?v=I_pQ7h6G8wk";
        urls[5][25] = "https://www.youtube.com/watch?v=XdPhu7eqPgk";//savings
        urls[5][26] = "https://www.youtube.com/watch?v=bjqd1hyq6jI";
        urls[5][27] = "https://www.youtube.com/watch?v=mv-NqTJKa4c";//digital
        urls[5][28] = "https://www.youtube.com/watch?v=OwRyo9VfwlU&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=45";
        urls[5][29] = "https://www.youtube.com/watch?v=U-y3t1CE_-o&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=30";
        urls[5][30] = "https://www.youtube.com/watch?v=5dYNFDnm__Y&index=15&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ";
        urls[5][31] = "https://www.youtube.com/watch?v=z2zRqaRtfig&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=48";
        urls[5][32] = "https://www.youtube.com/watch?v=yC1QRQIH2Co";
        urls[5][33] = "https://www.youtube.com/watch?v=rzWZxIDIduM";
        urls[5][34] = "https://www.youtube.com/watch?v=GYfQaPqV7w0&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=38";
        urls[5][35] = "https://www.youtube.com/watch?v=7Ug_91GKmw0&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=44";//others
        urls[5][36] = "https://www.youtube.com/watch?v=J04-bzauce0";
        urls[5][37] = "https://www.youtube.com/watch?v=pkKnJqlwvFA";
        urls[5][38] = "https://www.youtube.com/watch?v=uNYHiY7_NKU";

        //Oriya Video Links
        //compulsory video links
        urls[6][0] = "https://www.youtube.com/watch?v=UC2a4Y1bXuU";//responsible borrowing
        urls[6][1] = "---";
        urls[6][2] = "---";
        urls[6][3] = "---";//savings
        urls[6][4] = "---";
        urls[6][5] = "---";//insurance
        urls[6][6] = "---";
        urls[6][7] = "---";
        urls[6][8] = "---";//digital
        urls[6][9] = "---";
        urls[6][10] = "---";
        urls[6][11] = "---";
        urls[6][12] = "https://www.youtube.com/watch?v=0Zpi8v7rTxU";
        urls[6][13] = "---";
        urls[6][14] = "---";
        //desirable video links
        urls[6][15] = "---";//financial planning
        urls[6][16] = "---";
        urls[6][17] = "---";
        urls[6][18] = "https://www.youtube.com/watch?v=k_lcuPnGEys";//pension
        urls[6][19] = "---";//consumer beware
        urls[6][20] = "---";
        urls[6][21] = "---";//currency literacy
        urls[6][22] = "---";
        //supplementary video links
        urls[6][23] = "https://www.youtube.com/watch?v=3EhTbk5Vbw4";//loan
        urls[6][24] = "https://www.youtube.com/watch?v=7BeNx2h2sF4";//digital
        urls[6][25] = "https://www.youtube.com/watch?v=81NKLm3DFzs";
        urls[6][26] = "https://www.youtube.com/watch?v=QAP1G-im1U4&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=46";
        urls[6][27] = "https://www.youtube.com/watch?v=1jPPcIWY5G8&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=53";
        urls[6][28] = "https://www.youtube.com/watch?v=pR22MOQr0-Y&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=58";
        urls[6][29] = "https://www.youtube.com/watch?v=CzQvpAMrlJc";
        urls[6][30] = "https://www.youtube.com/watch?v=Gcc-avFIHBQ";
        urls[6][31] = "https://www.youtube.com/watch?v=DkTq6coo-XA&list=PLmhpqJCj6osbpypHqTcn3ROLRoxtBr9pY&index=4";//pension
        urls[6][32] = "https://www.youtube.com/watch?v=FWrhRTb4poc&list=PLPzJTdI3TPeuZoDa4NZNHH23oC6lOTAVZ&index=51";//others

        recyclerView = findViewById(R.id.customRecyclerView);

        blocks=new ArrayList<>();

        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        moduleName = getIntent().getStringExtra("module_name");
        choice=getIntent().getStringExtra("choice");
        language = preferences.getString("language", "en");

        /*
            "clicked" variable is used to check how the user is entering the app(as a user or as a group)
          if the user enters as a "group" then "Group id and name" will be shown on the module page and "Take attendance" function will be enabled
          if the user enters as a "user" then these functions are removed
          This variable is used at the bottom of this file in the "onPrepareOptionsMenu" function
          I am setting the preference for "clicked" variable at each point from where the user enters the "module" page
         */
        clicked = preferences.getString("clicked",null);

        /*
            "whichSubModule" variable is used to identify which submodule video the user has watched
            so that the "After quiz" will be prompted for that submodule only.
            I am setting the preference for "whichSubModule" variable in "quizAlert" function of "SubModule2Adapter.java" file
         */
        whichSubModule = preferences.getString("Which_submodule",null);

        /*
            "flag" variable is for "After Quiz".
            if it is true "After Quiz" will be prompted otherwise not.
            I am setting the preference for "flag" variable in "quizAlert" function of "SubModule2Adapter.java" file
         */
        flags = preferences.getBoolean("flag",false);
        //after quiz
        if(flags){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            builder.setTitle(R.string.what_u_learnt_from_vdo);
            builder.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(language.equals("en")){
                        afterEngQuiz();
                    } else if(language.equals("hi")){
                        afterHinQuiz();
                    }
                }
            });
            final AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(false);
            alert.setCancelable(false);
            alert.show();

        }

        switch (language) {
            case "en":
                getSubModulesEng();
                break;
            case "hi":
                getSubModulesHin();
                break;
            case "gu":
                getSubModulesGujarati();
                break;
            case "mr":
                getSubModulesMarathi();
                break;
            case "ta":
                getSubModulesTamil();
                break;
            case "kn":
                getSubModulesKannada();
                break;
            case "or":
                getSubModulesOriya();
                break;
        }

        adapter = new SubModule2Adapter(this,blocks,beforeQuestion,beforeOptionA,beforeOptionB,beforeOptionC,beforeOptionD,beforeAnswer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    public void getSubModulesEng(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[0][0],
                            "m1EngComp", "04:45", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[0][1],
                            "m1EngComp", "07:17", "MFIN"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer2), R.drawable.consumer2, urls[0][2],
                            "m1EngComp", "04:24", "MFIN"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[0][3],
                            "m2EngComp", "04:27", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving3guj, urls[0][4],
                            "m2EngComp", "03:38", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving3), R.drawable.saving2, urls[0][5],
                            "m2EngComp", "-", "-"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.insurance1, urls[0][6],
                            "m3EngComp", "05:39", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.insurance1, urls[0][7],
                            "m3EngComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance3), R.drawable.insurance2, urls[0][8],
                            "m3EngComp", "01:55", "Money Kraft"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other15), R.drawable.digital1, urls[0][9],
                            "m4EngComp", "03:00", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other16), R.drawable.consumer1, urls[0][10],
                            "m4EngComp", "01:47", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital3), R.drawable.digital_ussd_guj, urls[0][11],
                            "m3EngComp", "04:19", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.bhim), R.drawable.digital5, urls[0][12],
                            "m3EngComp", "01:45", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.aeps), R.drawable.digital2, urls[0][13],
                            "m3EngComp", "02:57", "NPCI"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[0][14],
                            "m1EngDesi", "14:27", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family2, urls[0][15],
                            "m1EngDesi", "04:20", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.family3, urls[0][16],
                            "m1EngDesi", "04:48", "NCFE"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension2), R.drawable.pension2, urls[0][17],
                            "m2EngDesi", "-", "-"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other16), R.drawable.consumer1, urls[0][18],
                            "m3EngDesi", "01:47", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer3), R.drawable.consumer5hin, urls[0][19],
                            "m3EngDesi", "-", "-"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency1), R.drawable.currency1, urls[0][20],
                            "m4EngDesi", "07:00", "RBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency2), R.drawable.currency2, urls[0][21],
                            "m4EngDesi", "05:51", "SBI"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other9),  R.drawable.loan2, urls[0][22],
                            "m1EngSupp", "27:57", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other10), R.drawable.loan1, urls[0][23],
                            "m1EngSupp", "22:00", "CSC"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other5),  R.drawable.saving1, urls[0][24],
                            "m2EngSupp", "35:28", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other6),  R.drawable.saving3guj, urls[0][25],
                            "m2EngSupp", "13:46", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other7), R.drawable.saving1, urls[0][26],
                            "m2EngSupp", "15:08", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other8), R.drawable.saving2, urls[0][27],
                            "m2EngSupp", "04:14", "NCFE"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other11), R.drawable.insurance1, urls[0][28],
                            "m3EngSupp", "20:03", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other12), R.drawable.other1eng, urls[0][29],
                            "m3EngSupp", "10:26", "ILO"));
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other14), R.drawable.digital6, urls[0][30],
                            "m4EngSupp", "36:32", "PMGDISHA"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other15), R.drawable.digital1, urls[0][31],
                            "m4EngSupp", "13:25", "NABARD"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other16), R.drawable.consumer1, urls[0][32],
                            "m4EngSupp", "03:19", "SBI"));
                    break;
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other1), R.drawable.family1, urls[0][33],
                            "m5EngSupp", "40:56", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other2), R.drawable.family1, urls[0][34],
                            "m5EngSupp", "14:27", "CSC"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension1),R.drawable.pension1, urls[0][35],
                            "m6EngSupp", "04:11", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other13), R.drawable.other_tata, urls[0][36],
                            "m6EngSupp", "03:06", "TATA Mutual Fund"));
                    break;
                case "Others"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other3),  R.drawable.family3, urls[0][37],
                            "m7EngSupp", "15:32", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other4),  R.drawable.other_kyc, urls[0][38],
                            "m7EngSupp", "00:30", "Axis Bank"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other17), R.drawable.other_tata, urls[0][39],
                            "m7EngSupp", "04:28", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other18), R.drawable.other_tata, urls[0][40],
                            "m7EngSupp", "04:19", "TATA Mutual Fund"));
                    break;
            }
        }
    }
    public void getSubModulesHin(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[1][1],
                            "m1HinComp", "08:42", "MFIN"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer1), R.drawable.consumer2, urls[1][2],
                            "m1HinComp", "04:27", "MFIN"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other6), R.drawable.vidya_balan, urls[1][22],
                            "m1HinSupp", "01:34", "MFIN"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[1][3],
                            "m2HinComp", "12:30", "PMJDY"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving2, urls[1][4],
                            "m2HinComp", "00:50", "NCFE"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.insurance1, urls[1][5],
                            "m3HinComp", "01:07", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.insurance2, urls[1][6],
                            "m3HinComp", "02:27", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer2), R.drawable.consumer2, urls[1][7],
                            "m3HinComp", "01:05", "IRDAI"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital1), R.drawable.digital1, urls[1][8],
                            "m4HinComp", "03:00", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer3), R.drawable.consumer1, urls[1][9],
                            "m4HinComp", "03:19", "RBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer7), R.drawable.consumer7hin, urls[1][10],
                            "m4HinComp", "01:30", "SBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.microatm), R.drawable.digital4, urls[1][11],
                            "m4HinComp", "05:31", "PMJDY"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.aeps), R.drawable.digital2, urls[1][12],
                            "m4HinComp", "02:57", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.ussd), R.drawable.digital_ussd_guj, urls[1][13],
                            "m4HinComp", "04:18", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.bhim), R.drawable.digital5, urls[1][14],
                            "m4HinComp", "01:28", "NPCI"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other2), R.drawable.family1, urls[1][15],
                            "m1HinDesi", "11:03", "NABARD"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other1), R.drawable.family2, urls[1][16],
                            "m1HinDesi", "40:51", "Parinaam Foundation"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.atal), R.drawable.pension2, urls[1][17],
                            "m2HinDesi", "02:32", "PFRDA"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer4), R.drawable.consumer4hin, urls[1][18],
                            "m3HinDesi", "00:35", "RBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer6), R.drawable.consumer6hin, urls[1][19],
                            "m3HinDesi", "00:36", "RBI"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency1), R.drawable.currency1, urls[1][20],
                            "m4HinDesi", "07:00", "RBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency2), R.drawable.currency2, urls[1][21],
                            "m4HinDesi", "05:51", "SBI"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[1][0],
                            "m1HinComp", "00:42", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other7), R.drawable.loan1, urls[1][23],
                            "m1HinSupp", "29:17", "Parinaam Foundation"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other8), R.drawable.loan1, urls[1][24],
                            "m1HinSupp", "08:51", "HDFC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other9), R.drawable.loan1, urls[1][25],
                            "m1HinSupp", "06:39", "PIB"));
                    break;
                case "Savings":
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other3), R.drawable.saving2, urls[1][26],
                            "m2HinSupp", "35:28", "Parinaam Foundation"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other4), R.drawable.saving1, urls[1][27],
                            "m2HinSupp", "22:01", "Parinaam Foundation"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other5), R.drawable.saving3guj, urls[1][28],
                            "m2HinSupp", "06:16", "MicroSave"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other10), R.drawable.other1eng, urls[1][29],
                            "m3HinSupp", "01:40", "ICICI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other11), R.drawable.other5, urls[1][30],
                            "m3HinSupp", "03:48", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other12), R.drawable.other4, urls[1][31],
                            "m3HinSupp", "06:17", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other13), R.drawable.other1, urls[1][32],
                            "m3HinSupp", "00:52", "PMJDY"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pjsby), R.drawable.other1, urls[1][33],
                            "m3HinSupp", "02:12", "MicroSave"));
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other17), R.drawable.digital4, urls[1][34],
                            "m4HinSupp", "08:14", "PMJDY"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other18), R.drawable.digital1, urls[1][35],
                            "m4HinSupp", "13:25", "NABARD"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other19), R.drawable.digital_ussd_guj, urls[1][36],
                            "m4HinSupp", "03:01", "NITI Aayog"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other20), R.drawable.digital2, urls[1][37],
                            "m4HinSupp", "02:42", "NITI Aayog"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.digital4, urls[1][38],
                            "m4HinSupp", "02:22", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital6), R.drawable.digital6, urls[1][39],
                            "m4HinSupp", "02:48", "CSC"));
                    break;
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family1, urls[1][40],
                            "m5HinSupp", "04:48", "MicroSave"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family2, urls[1][41],
                            "m5HinSupp", "05:58", "MicroSave"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension1), R.drawable.pension2, urls[1][42],
                            "m6HinSupp", "04:19", "BBDO"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension2), R.drawable.pension2, urls[1][43],
                            "m6HinSupp", "01:06", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other15),  R.drawable.pension1, urls[1][44],
                            "m6HinSupp", "03:16", "DD News"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other16),  R.drawable.pension1, urls[1][45],
                            "m6HinSupp", "01:07", "PFRDA"));
                    break;
                case "Others"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other21), R.drawable.other9, urls[1][46],
                            "m7HinSupp", "10:56", "HDFC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other_module3), R.drawable.other_kyc, urls[1][47],
                            "m7HinSupp", "00:30", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other22), R.drawable.other_kyc, urls[1][48],
                            "m7HinSupp", "00:35", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other23), R.drawable.other_tata, urls[1][49],
                            "m7HinSupp", "04:21", "Tata Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other24), R.drawable.other_tata, urls[1][50],
                            "m7HinSupp", "03:14", "Sundaram MF"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other25), R.drawable.other_tata, urls[1][51],
                            "m7HinSupp", "04:31", "Tata Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other26), R.drawable.other_tata, urls[1][52],
                            "m7HinSupp", "03:08", "Tata Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other27), R.drawable.other_tata, urls[1][53],
                            "m7HinSupp", "03:58", "Tata Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other28), R.drawable.other_tata, urls[1][54],
                            "m7HinSupp", "03:15", "Tata Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other29), R.drawable.other_tata, urls[1][55],
                            "m7HinSupp", "03:16", "Tata Mutual Fund"));
                    break;
            }
        }
    }
    public void getSubModulesGujarati(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[2][0],
                            "m1GujComp", "12:33", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[2][1],
                            "m1GujComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer2), R.drawable.consumer2, urls[2][2],
                            "m1GujComp", "-", "-"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[2][3],
                            "m2GujComp", "00:26", "PMJDY"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving2, urls[2][4],
                            "m2GujComp", "-", "-"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.insurance1, urls[2][5],
                            "m3GujComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.insurance2, urls[2][6],
                            "m3GujComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer3), R.drawable.consumer2, urls[2][7],
                            "m3GujComp", "-", "-"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital1), R.drawable.digital1, urls[2][8],
                            "m4GujComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer1), R.drawable.consumer1, urls[2][9],
                            "m4GujComp", "02:44", "Tv9 Gujarati"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital2), R.drawable.digital4, urls[2][10],
                            "m4GujComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital3), R.drawable.digital2, urls[2][11],
                            "m4GujComp", "00:55", "MIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital4), R.drawable.digital_ussd_guj, urls[2][12],
                            "m4GujComp", "00:50", "MIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.upi), R.drawable.digital6, urls[2][13],
                            "m4GujComp", "04:30", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital5), R.drawable.digital5, urls[2][14],
                            "m4GujComp", "05:10", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.phone), R.drawable.consumer7hin, urls[2][15],
                            "m4GujComp", "03:46", "Tv9 Gujarati"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[2][16],
                            "m1GujDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family2, urls[2][17],
                            "m1GujDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.family3, urls[2][18],
                            "m1GujDesi", "-", "-"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension1), R.drawable.pension2, urls[2][19],
                            "m2GujDesi", "00:38", "PFRDA"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer4), R.drawable.consumer2, urls[2][20],
                            "m3GujDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer5), R.drawable.consumer6hin, urls[2][21],
                            "m3GujDesi", "00:45", "NCFE"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency1), R.drawable.currency1, urls[2][22],
                            "m4GujDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency2), R.drawable.currency2, urls[2][23],
                            "m4GujDesi", "02:45", "Vtv Gujarati News"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[2][24],
                            "m1GujSupp", "18:10", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[2][25],
                            "m1GujSupp", "08:48", "HDFC"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.bb), R.drawable.other_misc, urls[2][26],
                            "m2GujSupp", "11:40", "HDFC"));
                    break;
                case "Insurance":
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other7), R.drawable.other_fund_transfer, urls[2][27],
                            "m4GujSupp", "00:31", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital6), R.drawable.digital4, urls[2][28],
                            "m4GujSupp", "00:45", "MIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital7), R.drawable.digital6, urls[2][29],
                            "m4GujSupp", "00:55", "MIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.dfs), R.drawable.digital6, urls[2][30],
                            "m4GujSupp", "39:26", "PMGDISHA"));
                    break;
                case "Financial Planning and Budgeting":
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.rp), R.drawable.other_tata, urls[2][31],
                            "m6GujSupp", "03:07", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension2), R.drawable.pension1, urls[2][32],
                            "m6GujSupp", "01:05", "PFRDA"));
                    break;
                case "Others":
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.tpc), R.drawable.other_tata, urls[2][33],
                            "m7GujSupp", "04:21", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other8), R.drawable.other_tata, urls[2][34],
                            "m7GujSupp", "04:29", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other6), R.drawable.other_kyc, urls[2][35],
                            "m7GujSupp", "00:30", "NCFE"));
                    break;
            }
        }
    }
    public void getSubModulesMarathi(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[3][0],
                            "m1MarComp", "-", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[3][1],
                            "m1MarComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer2), R.drawable.consumer2, urls[3][2],
                            "m1MarComp", "-", "-"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[3][3],
                            "m2MarComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving2, urls[3][4],
                            "m2MarComp", "-", "-"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.insurance1, urls[3][5],
                            "m3MarComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.insurance2, urls[3][6],
                            "m3MarComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer3), R.drawable.consumer2, urls[3][7],
                            "m3MarComp", "-", "-"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital1), R.drawable.digital1, urls[3][8],
                            "m4MarComp", "03:02", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer1), R.drawable.consumer1, urls[3][9],
                            "m4MarComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital2), R.drawable.digital4, urls[3][10],
                            "m4MarComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital3), R.drawable.digital2, urls[3][11],
                            "m4MarComp", "00:56", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital4), R.drawable.digital_ussd_guj, urls[3][12],
                            "m4MarComp", "00:51", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital5), R.drawable.digital5, urls[3][13],
                            "m4MarComp", "05:10", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer6), R.drawable.consumer7hin, urls[3][14],
                            "m4MarComp", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[3][15],
                            "m1MarDesi", "20:08", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family2, urls[3][16],
                            "m1MarDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.family3, urls[3][17],
                            "m1MarDesi", "-", "-"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension1), R.drawable.pension2, urls[3][18],
                            "m2MarDesi", "-", "-"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer4), R.drawable.consumer2, urls[3][19],
                            "m3MarDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer5), R.drawable.consumer6hin, urls[3][20],
                            "m3MarDesi", "00:37", "RBI"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency1), R.drawable.currency1, urls[3][21],
                            "m4MarDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency2), R.drawable.currency2, urls[3][22],
                            "m4MarDesi", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loanbank), R.drawable.loan1, urls[3][23],
                            "m1MarSupp", "17:20", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[3][24],
                            "m1MarSupp", "08:48", "HDFC"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.savings), R.drawable.saving1, urls[3][25],
                            "m2MarSupp", "11:15", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.hb), R.drawable.saving1, urls[3][26],
                            "m2MarSupp", "17:47", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.bb), R.drawable.other_misc, urls[3][27],
                            "m2MarSupp", "11:39", "HDFC"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.ipp), R.drawable.insurance1, urls[3][28],
                            "m3MarSupp", "15:18", "CSC"));
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other5), R.drawable.digital6, urls[3][29],
                            "m4MarSupp", "01:00", "Niti Ayog"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other8), R.drawable.other_fund_transfer, urls[3][30],
                            "m4MarSupp", "00:31", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other10), R.drawable.digital5, urls[3][31],
                            "m4MarSupp", "00:51", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other13), R.drawable.digital6, urls[3][32],
                            "m4MarSupp", "04:30", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other11), R.drawable.digital6, urls[3][33],
                            "m4MarSupp", "38:10", "PMGDISHA"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other12), R.drawable.other_kyc, urls[3][34],
                            "m4MarSupp", "00:34", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumerI), R.drawable.consumer6hin, urls[3][35],
                            "m4MarSupp", "00:44", "RBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumerII), R.drawable.consumer6hin, urls[3][36],
                            "m4MarSupp", "0:45", "NCFE"));
                    break;
                case "Financial Planning and Budgeting":
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension2), R.drawable.pension1, urls[3][37],
                            "m6MarSupp", "01:05", "NCFE"));
                    break;
                case "Others"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other6), R.drawable.family3, urls[3][38],
                            "m7MarSupp", "14:45", "CSC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other7), R.drawable.other_kyc, urls[3][39],
                            "m7MarSupp", "00:30", "NCFE"));
                    break;
            }
        }
    }
    public void getSubModulesTamil(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[4][0],
                            "m1TamComp", "17:56", "Ujjivan and others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[4][1],
                            "m1TamComp", "08:06", "MFIN"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.consumer3), R.drawable.consumer2, urls[4][2],
                            "m1TamComp", "04:59", "MFIN"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[4][3],
                            "m2TamComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving2, urls[4][4],
                            "m2TamComp", "00:40", "RBI"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.insurance1, urls[4][5],
                            "m3TamComp", "03:01", "Puthiya TV"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.insurance2, urls[4][6],
                            "m3TamComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance3), R.drawable.consumer2, urls[4][7],
                            "m3TamComp", "00:55", "NCFE"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital1), R.drawable.digital1, urls[4][8],
                            "m4TamComp", "03:02", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital2), R.drawable.consumer1, urls[4][9],
                            "m4TamComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital6), R.drawable.digital4, urls[4][10],
                            "m4TamComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital3), R.drawable.digital2, urls[4][11],
                            "m4TamComp", "03:01", "NPCI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital5), R.drawable.digital_ussd_guj, urls[4][12],
                            "m4TamComp", "05:29", "Vignesh KM"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital4), R.drawable.digital5, urls[4][13],
                            "m4TamComp", "01:23", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.phone), R.drawable.consumer7hin, urls[4][14],
                            "m4TamComp", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[4][15],
                            "m1TamDesi", "04:16", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family2, urls[4][16],
                            "m1TamDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.family3, urls[4][17],
                            "m1TamDesi", "-", "-"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension1), R.drawable.pension2, urls[4][18],
                            "m2TamDesi", "00:38", "PFRDA"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.customerliable), R.drawable.consumer4hin, urls[4][19],
                            "m3TamDesi", "00:30", "RBI"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.email), R.drawable.consumer6hin, urls[4][20],
                            "m3TamDesi", "00:37", "RBI"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency1), R.drawable.currency1, urls[4][21],
                            "m4TamDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency2), R.drawable.currency2, urls[4][22],
                            "m4TamDesi", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[4][23],
                            "m1TamSupp", "11:45", "HDFC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other4), R.drawable.loan1, urls[4][24],
                            "m1TamSupp", "14:03", "Ujjivan and Others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other5), R.drawable.loan1, urls[4][25],
                            "m1TamSupp", "17:58", "Ujjivan and Others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other6), R.drawable.loan2, urls[4][26],
                            "m1TamSupp", "01:03", "JFS"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving3guj, urls[4][27],
                            "m2TamSupp", "04:07", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving1, urls[4][28],
                            "m2TamSupp", "17:34", "Ujjivan and Others"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.other_misc, urls[4][29],
                            "m2TamSupp", "11:39", "HDFC"));
                    break;
                case "Insurance":
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digitals1), R.drawable.digital4, urls[4][30],
                            "m4TamSupp", "01:23", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digitals2), R.drawable.digital6, urls[4][31],
                            "m4TamSupp", "04:54", "StoriesInTamil"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digitals3), R.drawable.consumer6hin, urls[4][32],
                            "m4TamSupp", "00:45", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digitals4), R.drawable.digital5, urls[4][33],
                            "m4TamSupp", "01:23", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digitals5), R.drawable.digital6, urls[4][34],
                            "m4TamSupp", "04:30", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digitals6), R.drawable.other_kyc, urls[4][35],
                            "m4TamSupp", "00:34", "NCFE"));
                    break;
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[4][36],
                            "m5TamSupp", "19:41", "Ujjivan and Others"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.rp), R.drawable.other_tata, urls[4][37],
                            "m6TamSupp", "03:07", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension2), R.drawable.pension1, urls[4][38],
                            "m6TamSupp", "01:05", "NPS"));
                    break;
                case "Others"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.kyc), R.drawable.other_kyc, urls[4][39],
                            "m7TamSupp", "00:30", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.poc), R.drawable.other_tata, urls[4][40],
                            "m7TamSupp", "04:23", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.im), R.drawable.other_tata, urls[4][41],
                            "m7TamSupp", "04:30", "TATA Mutual Fund"));
                    break;
            }
        }
    }
    public void getSubModulesKannada(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[5][0],
                            "m1KanComp", "17:56", "Parinaam Foundation"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[5][1],
                            "m1KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan3), R.drawable.consumer2, urls[5][2],
                            "m1KanComp", "-", "-"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[5][3],
                            "m2KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving2, urls[5][4],
                            "m2KanComp", "00:40", "RBI"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.insurance1, urls[5][5],
                            "m3KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.insurance2, urls[5][6],
                            "m3KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.consumer2, urls[5][7],
                            "m3KanComp", "00:54", "NCFE"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital1), R.drawable.digital1, urls[5][8],
                            "m4KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital8), R.drawable.consumer1, urls[5][9],
                            "m4KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital2), R.drawable.digital4, urls[5][10],
                            "m4KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital3), R.drawable.digital2, urls[5][11],
                            "m4KanComp", "00:55", "MIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital4), R.drawable.digital_ussd_guj, urls[5][12],
                            "m4KanComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital5), R.drawable.digital5, urls[5][13],
                            "m4KanComp", "05:10", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital7), R.drawable.consumer7hin, urls[5][14],
                            "m4KanComp", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[5][15],
                            "m1KanDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family2, urls[5][16],
                            "m1KanDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.family3, urls[5][17],
                            "m1KanDesi", "-", "-"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.pension1), R.drawable.pension2, urls[5][18],
                            "m2KanDesi", "00:38", "NPS"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.con1), R.drawable.consumer4hin, urls[5][19],
                            "m3KanDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.con2), R.drawable.consumer6hin, urls[5][20],
                            "m3KanDesi", "-", "-"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency1), R.drawable.currency1, urls[5][21],
                            "m4KanDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.currency2), R.drawable.currency2, urls[5][22],
                            "m4KanDesi", "03:09", "vinodh ramesh"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.loan2, urls[5][23],
                            "m1KanSupp", "11:45", "HDFC"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.loan1, urls[5][24],
                            "m1KanSupp", "18:53", "Parinaam Foundation"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.savings1), R.drawable.saving1, urls[5][25],
                            "m2KanSupp", "19:49", "Parinaam Foundation"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.savings2), R.drawable.other_misc, urls[5][26],
                            "m2KanSupp", "11:39", "HDFC"));
                    break;
                case "Insurance":
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital6), R.drawable.digital4, urls[5][27],
                            "m4KanSupp", "00:45", "MIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.email), R.drawable.consumer6hin, urls[5][28],
                            "m4KanSupp", "00:45", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.digital9), R.drawable.digital5, urls[5][29],
                            "m4KanSupp", "01:24", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.upi), R.drawable.other_kyc, urls[5][30],
                            "m4KanSupp", "04:30", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other6), R.drawable.other_fund_transfer, urls[5][31],
                            "m4KanSupp", "01:31", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other7), R.drawable.digital6, urls[5][32],
                            "m4KanSupp", "01:00", "MyGovIndia"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other8), R.drawable.digital6, urls[5][33],
                            "m4KanSupp", "30:50", "PMGDISHA"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.other9), R.drawable.other_kyc, urls[5][34],
                            "m4KanSupp", "00:34", "NCFE"));
                    break;
                case "Financial Planning and Budgeting":
                    break;
                case "Pension":
                    break;
                case "Others"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.kyc), R.drawable.other_kyc, urls[5][35],
                            "m7KanSupp", "00:31", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.tpc), R.drawable.other_tata, urls[5][36],
                            "m7KanSupp", "04:21", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.rp), R.drawable.other_tata, urls[5][37],
                            "m7KanSupp", "03:07", "TATA Mutual Fund"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.im), R.drawable.other_tata, urls[5][38],
                            "m7KanSupp", "04:29", "TATA Mutual Fund"));
                    break;
            }
        }
    }
    public void getSubModulesOriya(){
        if(choice.equals("compulsory")){
            switch (moduleName) {
                case "Responsible Borrowing"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.loan1, urls[6][0],
                            "m1OriComp", "17:57", "Parinaam Foundation"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.loan2, urls[6][1],
                            "m1OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.consumer2, urls[6][2],
                            "m1OriComp", "-", "-"));
                    break;
                case "Savings"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.saving1, urls[6][3],
                            "m2OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving2), R.drawable.saving2, urls[6][4],
                            "m2OriComp", "-", "-"));
                    break;
                case "Insurance"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.insurance1, urls[6][5],
                            "m3OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.insurance2, urls[6][6],
                            "m3OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.consumer2, urls[6][7],
                            "m3OriComp", "-", "-"));
                    break;
                case "Digital Transaction"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.digital1, urls[6][8],
                            "m4OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.consumer1, urls[6][9],
                            "m4OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.digital4, urls[6][10],
                            "m4OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.digital2, urls[6][11],
                            "m4OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.digital_ussd_guj, urls[6][12],
                            "m4OriComp", "00:50", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.digital5, urls[6][13],
                            "m4OriComp", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.consumer7hin, urls[6][14],
                            "m4OriComp", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("desirable"))
        {
            switch (moduleName) {
                case "Financial Planning and Budgeting"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.family1, urls[6][15],
                            "m1OriDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family2), R.drawable.family2, urls[6][16],
                            "m1OriDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family3), R.drawable.family3, urls[6][17],
                            "m1OriDesi", "-", "-"));
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.saving1), R.drawable.pension2, urls[6][18],
                            "m2OriDesi", "00:31", "PFRDA"));
                    break;
                case "Consumer Beware"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan1), R.drawable.consumer4hin, urls[6][19],
                            "m3OriDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.loan2), R.drawable.consumer6hin, urls[6][20],
                            "m3OriDesi", "-", "-"));
                    break;
                case "Currency Literacy"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.currency1, urls[6][21],
                            "m4OriDesi", "-", "-"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.currency2, urls[6][22],
                            "m4OriDesi", "-", "-"));
                    break;
            }
        }
        else if(choice.equals("suppl"))
        {
            switch (moduleName) {
                case "Loan"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.family1), R.drawable.loan2, urls[6][23],
                            "m1OriSupp", "08:48", "HDFC"));
                    break;
                case "Savings":
                    break;
                case "Insurance":
                    break;
                case "Digital Transactions"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.digital4, urls[6][24],
                            "m4OriSupp", "00:45", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.digital6, urls[6][25],
                            "m4OriSupp", "00:55", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.consumer6hin, urls[6][26],
                            "m4OriSupp", "00:45", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.other_fund_transfer, urls[6][27],
                            "m4OriSupp", "01:31", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.other_kyc, urls[6][28],
                            "m4OriSupp", "00:34", "NCFE"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.digital6, urls[6][29],
                            "m4OriSupp", "00:50", "PIB"));
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance2), R.drawable.digital6, urls[6][30],
                            "m4OriSupp", "37:42", "PMGDISHA"));
                    break;
                case "Financial Planning and Budgeting":
                    break;
                case "Pension"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.pension1, urls[6][31],
                            "m6OriSupp", "01:05", "PFRDA"));
                    break;
                case "Others"://image ok
                    blocks.add(new SubModule2Blocks(getResources().getString(R.string.insurance1), R.drawable.other_kyc, urls[6][32],
                            "m7OriSupp", "00:31", "NCFE"));
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        switch (choice) {
            case "compulsory": {
                Intent intent = new Intent(SubModule2.this, ModuleCompulsory.class);
                startActivity(intent);
                finish();
                break;
            }
            case "desirable": {
                Intent intent = new Intent(SubModule2.this, ModuleDesirable.class);
                startActivity(intent);
                finish();
                break;
            }
            case "suppl": {
                Intent intent = new Intent(SubModule2.this, ModuleSupplementary.class);
                startActivity(intent);
                finish();
                break;
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on back pressed
        if (id == android.R.id.home) {
            switch (choice) {
                case "compulsory": {
                    Intent intent = new Intent(SubModule2.this, ModuleCompulsory.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case "desirable": {
                    Intent intent = new Intent(SubModule2.this, ModuleDesirable.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case "suppl": {
                    Intent intent = new Intent(SubModule2.this, ModuleSupplementary.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }

        }
        if (id == R.id.about) {
            Intent intent = new Intent(SubModule2.this, About.class);
            startActivity(intent);
        }
        if (id == R.id.attendance) {
            Intent intent = new Intent(SubModule2.this, Attendance.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (clicked.equals("User")) {
            menu.findItem(R.id.attendance).setEnabled(false).setVisible(false);//hiding the "Take Attendance" menu for normal user
        }
        return true;
    }
    public void quizAlert(int a, int flag){
        flag++;
        noOfClicks=0;
        questionNo = a;
        final String question,correctAns;
        final String[] option = new String[4];
        question = afterQuestion[a];
        option[0] = afterOptionA[a];
        option[1] = afterOptionB[a];
        option[2] = afterOptionC[a];
        option[3] = afterOptionD[a];
        correctAns = afterAnswer[a];

        final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(SubModule2.this);
        LayoutInflater inflater1 = (LayoutInflater) SubModule2.this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView1 = inflater1.inflate(R.layout.after_quiz, null);
        dialogBuilder1.setView(dialogView1);

        TextView Question1 = dialogView1.findViewById(R.id.tvQue);
        final TextView crctAns = dialogView1.findViewById(R.id.tvCorrectAns);
        final RadioGroup radioGroup = dialogView1.findViewById(R.id.rg_option_AQ);
        RadioButton op1 = dialogView1.findViewById(R.id.rb_option1);
        RadioButton op2 = dialogView1.findViewById(R.id.rb_option2);
        RadioButton op3 = dialogView1.findViewById(R.id.rb_option3);
        RadioButton op4 = dialogView1.findViewById(R.id.rb_option4);
        Button button = dialogView1.findViewById(R.id.buttonNext_AQ);
        op1.setVisibility(View.GONE);
        op2.setVisibility(View.GONE);
        op3.setVisibility(View.GONE);
        op4.setVisibility(View.GONE);

        Question1.setText(question);

        if(!option[0].equals("")){
            op1.setVisibility(View.VISIBLE);
            op1.setText(option[0]);
        }
        if (!option[1].equals("")){
            op2.setVisibility(View.VISIBLE);
            op2.setText(option[1]);
            if (!option[2].equals("")){
                op3.setVisibility(View.VISIBLE);
                op3.setText(option[2]);
                if (!option[3].equals("")){
                    op4.setVisibility(View.VISIBLE);
                    op4.setText(option[3]);
                }
            }
        }

        final AlertDialog alertDialog = dialogBuilder1.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();

        final int finalFlag = flag;

        //converting button text from "Next" to "Done"
        if(flag == 2)
            button.setText(getResources().getString(R.string.doneButton));

        //positive button OnClick
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                noOfClicks++;
                Boolean wantToCloseDialog = false;
                int idx = radioGroup.getCheckedRadioButtonId();
                View radiobutton = radioGroup.findViewById(idx);
                int selectedPosition = radioGroup.indexOfChild(radiobutton);
                if(selectedPosition>=0)
                {
                    if(noOfClicks==1){//displaying correct ans on first click
                        radioGroup.setClickable(false);
                        if(option[selectedPosition].equals(correctAns)){
                            crctAns.setVisibility(View.VISIBLE);
                            crctAns.setText(getResources().getString(R.string.correct_answer));
                            crctAns.setTextColor(getResources().getColor(R.color.green));
                        } else {
                            crctAns.setVisibility(View.VISIBLE);
                            crctAns.setText(getResources().getString(R.string.ans_should_b) + correctAns + "' !!!");
                            crctAns.setTextColor(getResources().getColor(R.color.red));
                        }
                    } else if(noOfClicks==2){//moving to next question on second click
                        if(option[selectedPosition].equals(correctAns)){
                            crctAns.setVisibility(View.VISIBLE);
                            crctAns.setText(getResources().getString(R.string.correct_answer));
                            crctAns.setTextColor(getResources().getColor(R.color.green));
                        } else {
                            crctAns.setVisibility(View.VISIBLE);
                            crctAns.setText(getResources().getString(R.string.ans_should_b) + correctAns + "' !!!");
                            crctAns.setTextColor(getResources().getColor(R.color.red));
                        }
                        questionNo++;
                        if(finalFlag == 2)
                        {
                            //making the flag false so that after quiz will not be asked again
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("flag",false);
                            editor.apply();

                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SubModule2.this);
                            LayoutInflater inflater = (LayoutInflater) SubModule2.this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            View dialogView = inflater.inflate(R.layout.like_dislike, null);
                            dialogBuilder.setView(dialogView);
                            Button noLikeButton = dialogView.findViewById(R.id.noLikeButton);
                            Button yesLikeButton = dialogView.findViewById(R.id.yesLikeButton);

                            final AlertDialog alert2 = dialogBuilder.create();
                            alert2.setCanceledOnTouchOutside(false);
                            alert2.setCancelable(false);
                            alert2.show();

                            noLikeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alert2.dismiss();
                                }
                            });
                            yesLikeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alert2.dismiss();
                                }
                            });

                            wantToCloseDialog = true;
                        }
                        else
                        {
                            quizAlert(questionNo, finalFlag);
                            wantToCloseDialog = true;
                        }
                    }
                }
                else
                {
                    Toast.makeText(SubModule2.this, R.string.please_select_an_option, Toast.LENGTH_SHORT).show();
                    wantToCloseDialog = false;
                }
                if(wantToCloseDialog)
                    alertDialog.dismiss();
            }
        });
    }
    private void loadLang() {
        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        String langCode = preferences.getString("language", "en");

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void afterEngQuiz(){
        switch (whichSubModule) {
            case "Borrowing"://compulsory video starts here
                quizAlert(10,0);
                break;
            case "Credit Literacy":
                quizAlert(12,0);
                break;
            case "Grievance Redressal –For Loan Client":
                quizAlert(34,0);
                break;
            case "Importance of Savings":
                quizAlert(6,0);
                break;
            case "Savings in Bank":
                quizAlert(8,0);
                break;
            case "Types of Insurance":
                quizAlert(14,0);
                break;
            case "ATM":
                quizAlert(20,0);
                break;
            case "ATM Debit Card - Fraud and Prevention":
                quizAlert(32,0);
                break;
            case "USSD (*99#)":
                quizAlert(24,0);
                break;
            case "BHIM App":
                quizAlert(26,0);
                break;
            case "Aadhaar Enabled Payment System (AEPS)"://compulsory video ends here
                quizAlert(22,0);
                break;
            case "Financial Planning"://desirable video starts here
                quizAlert(0,0);
                break;
            case "Budgeting":
                quizAlert(2,0);
                break;
            case "Needs and Wants":
                quizAlert(4,0);
                break;
            case "Atal Pension Yojana":
                quizAlert(18,0);
                break;
            case "Security Features of Rs.100":
                quizAlert(36,0);
                break;
            case "Security Features of Rs.500 and Rs.2000"://desirable video ends here
                quizAlert(38,0);
                break;
        }
    }
    public void afterHinQuiz(){
        switch (whichSubModule) {
            case "ऋण माफ़ी अफवाह"://compulsory video starts here
                quizAlert(8,0);
                break;
            case "ऋण साक्षरता":
                quizAlert(10,0);
                break;
            case "ऋण शिकायत निवारण":
                quizAlert(32,0);
                break;
            case "प्रधानमंत्री जन धन योजना":
                quizAlert(4,0);
                break;
            case "बुनियादी बचत बैंक जमा खाता (BSBDA)":
                quizAlert(6,0);
                break;
            case "प्रधानमंत्री जीवन ज्योति बीमा योजना":
                quizAlert(12,0);
                break;
            case "प्रधानमंत्री जीवन जन सुरक्षा बीमा योजना":
                quizAlert(14,0);
                break;
            case "बीमा शिकायत निवारण":
                quizAlert(34,0);
                break;
            case "एटीएम (ATM)":
                quizAlert(20,0);
                break;
            case "एटीएम डेबिट कार्ड - धोखाधड़ी और निवारण":
                quizAlert(36,0);
                break;
            case "फ़ोन द्वारा धोखाधड़ी":
                quizAlert(42,0);
                break;
            case "माइक्रो एटीएम":
                quizAlert(28,0);
                break;
            case "आधार सक्षम भुगतान प्रणाली (AEPS)":
                quizAlert(22,0);
                break;
            case "यूएसएसडी (*99#)":
                quizAlert(24,0);
                break;
            case "भीम (BHIM) एप"://compulsory video ends here
                quizAlert(26,0);
                break;
            case "वित्तीय योजना और लक्ष्य"://desirable video starts here
                quizAlert(0,0);
                break;
            case "वित्तीय योजना":
                quizAlert(2,0);
                break;
            case "अटल पेंशन योजना":
                quizAlert(16,0);
                break;
            case "डिजिटल धोखाधड़ी होने पे क्या करें":
                quizAlert(38,0);
                break;
            case "ईमेल(Email) द्वारा धोखाधड़ी":
                quizAlert(40,0);
                break;
            case "100 रुपये की सुरक्षा विशेषताएं":
                quizAlert(44,0);
                break;
            case "500 रुपये और 2000 रुपये की सुरक्षा विशेषताएं"://desirable video ends here
                quizAlert(46,0);
                break;
        }
    }
}

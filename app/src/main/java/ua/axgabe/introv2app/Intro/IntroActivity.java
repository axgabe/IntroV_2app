package ua.axgabe.introv2app.Intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import ua.axgabe.introv2app.MainActivity;
import ua.axgabe.introv2app.R;

public class IntroActivity extends AppCompatActivity {

    private IntroAdapter introAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private MaterialButton btnIntroAction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Активити на весь экран
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);


        // Делаем проверку было ли раньше у пользователя окно приведствия
        if (restorePrefData()){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        layoutOnboardingIndicator = findViewById(R.id.IntroLayoutOnboardingIndicators);
        btnIntroAction = findViewById(R.id.IntroBtnOnboardingAction);

        setupIntroItems();

        final ViewPager2 introViewPager2 = findViewById(R.id.IntroOnboardingViewPager2);
        introViewPager2.setAdapter(introAdapter);

        // Индикатор отслеживание окон
        setupIntroOnboardingIndicator();

        // Индикатор отслеживание открытых окон
        setCurrentOnboardingIndicator(0);

        // Индикатор отслеживание открытых окон отслеживание действий
        introViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        btnIntroAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(introViewPager2.getCurrentItem() + 1 < introAdapter.getItemCount()) {
                    introViewPager2.setCurrentItem(introViewPager2.getCurrentItem() + 1);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    // Метод для хранения в кеше
                    savePrefsData();
                    //
                    finish();
                }
            }
        });


    }

    private boolean restorePrefData (){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;

    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }



    // Установка обьектав к переменым
    private void setupIntroItems(){

        List<IntroModel> introModels = new ArrayList<>();

        // Первый метод добавки

        introModels.add(new IntroModel(R.string.IntroTitleOne,R.string.IntroDescriptionOne,R.drawable.coming_soon_1));
        introModels.add(new IntroModel(R.string.IntroTitleTwo,R.string.IntroDescriptionTwo,R.drawable.coming_soon_2));
        introModels.add(new IntroModel(R.string.IntroTitleThree,R.string.IntroDescriptionThree,R.drawable.coming_soon_3));

        // Второй метод добавки
        /*
        IntroModel itemIntroOne = new IntroModel();
        itemIntroOne.setTitle(R.string.IntroTitleOne);
        itemIntroOne.setDescription(R.string.IntroDescriptionOne);
        itemIntroOne.setImg(R.drawable.coming_soon_1);

        IntroModel itemIntroTwo = new IntroModel();
        itemIntroOne.setTitle(R.string.IntroTitleTwo);
        itemIntroOne.setDescription(R.string.IntroDescriptionTwo);
        itemIntroOne.setImg(R.drawable.coming_soon_2);

        IntroModel itemIntroThree = new IntroModel();
        itemIntroOne.setTitle(R.string.IntroTitleThree);
        itemIntroOne.setDescription(R.string.IntroDescriptionThree);
        itemIntroOne.setImg(R.drawable.coming_soon_3);

        introModels.add(itemIntroOne);
        introModels.add(itemIntroTwo);
        introModels.add(itemIntroThree);
        */
        // Конец добавки

        introAdapter = new IntroAdapter(introModels);

    }

    // Intro и Onboarding = можно считать одинаковым :)
    // Установка индикатора слежения за вкладками присваивание движения.
    private void setupIntroOnboardingIndicator (){

        ImageView[] indicators = new ImageView[introAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8,0,8,0);

        for (int i = 0; i < indicators.length; i++){

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }

    }

    private void setCurrentOnboardingIndicator (int index){

        int childCount  = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutOnboardingIndicator.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        // Конец циклу
        // Изменяем название кнопки при последнем окне
        if (index == introAdapter.getItemCount()-1){
            btnIntroAction.setText(R.string.IntroBtnGetStarted);
        }else {
            btnIntroAction.setText(R.string.IntroBtnNext);
        }
    }



}
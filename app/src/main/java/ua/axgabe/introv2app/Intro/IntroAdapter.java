package ua.axgabe.introv2app.Intro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ua.axgabe.introv2app.R;


public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {

    private List<IntroModel> introModels;

    public IntroAdapter(List<IntroModel> introModels) {
        this.introModels = introModels;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntroViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.intro_item_container_onboarding, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.setIntroData(introModels.get(position));
    }

    @Override
    public int getItemCount() {
        return introModels.size();
    }

    // Инцилизация обьектов
    class IntroViewHolder extends RecyclerView.ViewHolder {

    private TextView textTitle;
    private TextView textDescription;
    private ImageView imgIntro;

        IntroViewHolder(@NonNull View itemView) {
        super(itemView);

            // Поиск обьектов
            textTitle = itemView.findViewById(R.id.intro_item_textTitle);
            textDescription = itemView.findViewById(R.id.intro_item_textDescription);
            imgIntro = itemView.findViewById(R.id.intro_item_img_onboarding);

            }

        void setIntroData (IntroModel introModel){

            // Присваивание обьектов к модели
            textTitle.setText(introModel.getTitle());
            textDescription.setText(introModel.getDescription());
            imgIntro.setImageResource(introModel.getImg());

        }


    }

}

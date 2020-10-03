package ua.axgabe.introv2app.Intro;

public class IntroModel {

    private int title, description, img;

    public IntroModel() {
    }

    // Модель для инцилизации - Заголовка, описания, и изображения для Масива
    public IntroModel(int title, int description, int img) {
        this.title = title;
        this.description = description;
        this.img = img;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

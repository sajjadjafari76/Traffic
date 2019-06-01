package irstit.transport.DataModel;

import android.graphics.drawable.Drawable;

public class NavModel {

    private String Name;
    private Drawable Image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Drawable getImage() {
        return Image;
    }

    public void setImage(Drawable image) {
        Image = image;
    }
}

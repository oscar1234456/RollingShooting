
package com.greatspace.proxy;

import javax.swing.ImageIcon;
import com.greatspace.interfaces.IImage;


public class RealImage implements IImage {

    private final String path;
    private ImageIcon imageIcon;

    public RealImage(String path) {
        this.path = path;
    }

    @Override
    public ImageIcon loadImage() {
        if (imageIcon == null) {
            imageIcon = new ImageIcon(getClass().getResource(path));
        }
        return imageIcon;
    }

}

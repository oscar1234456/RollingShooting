
package com.greatspace.proxy;

import javax.swing.ImageIcon;
import com.greatspace.interfaces.IImage;


public class ProxyImage implements IImage {

    private RealImage realImage;
    private final String path; //路徑常數

    public ProxyImage(String path) {
        this.path = path;
    }

    @Override
    public ImageIcon loadImage() {

        if (this.realImage == null) {
            this.realImage = new RealImage(this.path);
        }

        return this.realImage.loadImage();
    }

}

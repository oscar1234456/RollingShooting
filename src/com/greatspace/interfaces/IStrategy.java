
package com.greatspace.interfaces;

import com.greatspace.model.Player;
import java.awt.event.KeyEvent;


public interface IStrategy {
    public void keyPressed(Player player, KeyEvent key);
    public void keyReleased(Player player, KeyEvent key);
}

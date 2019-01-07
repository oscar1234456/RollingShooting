/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greatspace.controller;

import com.greatspace.interfaces.IStrategy;
import com.greatspace.model.Player;
import com.greatspace.model.Music;
import java.awt.event.KeyEvent;

/**
 *
 * @author Dayvson
 */
public enum Controller implements IStrategy { //列舉
	
    PLAYER_1 {
    	Music music = new Music();
        @Override
        public void keyPressed(Player player, KeyEvent key){
           System.out.println("keyPressed2 s");
        	int codigo = key.getKeyCode();
            if (!player.isMorto()) { //如果玩家沒死時
                switch(codigo){
                    case KeyEvent.VK_G: //發射飛彈  
                        player.atira();
                        music.shoot1Start(); 	
                        System.out.println("Fire");
                        break;
                    case KeyEvent.VK_W:
                        player.setDy(-1);
                        System.out.println("up");
                        break;
                    case KeyEvent.VK_S:
                        player.setDy(1);
                        System.out.println("down");
                        break;
                    case KeyEvent.VK_A:
                        player.setDx(-1);
                        System.out.println("left");
                        break;
                    case KeyEvent.VK_D:
                        player.setDx(1);
                        System.out.println("right");
                        break;
                   
                    default:
                    	System.out.println("none");
                }
            }
        }
        
        @Override
        public void keyReleased(Player player, KeyEvent key){
            int codigo = key.getKeyCode();
            if (!player.isMorto()) {
                switch(codigo){
	                case KeyEvent.VK_G: //發射飛彈  
	                    music.shoot1Start(); 	
	                    //System.out.println("Fire");
	                    break;
                    case KeyEvent.VK_W:
                        player.setDy(0);
                        System.out.println("up2");
                        break;
                    case KeyEvent.VK_S:
                        player.setDy(0);
                        System.out.println("down2");
                        break;
                    case KeyEvent.VK_A:
                        player.setDx(0);
                        System.out.println("left2");
                        break;
                    case KeyEvent.VK_D:
                        player.setDx(0);
                        System.out.println("right2");
                        break;
                    default:
                    	System.out.println("none2");
                }
            }
        }
    },
    PLAYER_2 {
    	Music music = new Music();
        @Override
        public void keyPressed(Player player, KeyEvent key){
            int codigo = key.getKeyCode();
            if (!player.isMorto()) {
                switch(codigo){
                    case KeyEvent.VK_INSERT:
                        player.atira();
                        music.shoot2Start();
                        break;
                    case KeyEvent.VK_UP:
                        player.setDy(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        player.setDy(1);
                        break;
                    case KeyEvent.VK_LEFT:
                        player.setDx(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setDx(1);
                        break;
                }
            }
        }
        
        @Override
        public void keyReleased(Player player, KeyEvent key){
            int codigo = key.getKeyCode();
            if (!player.isMorto()) {
                switch(codigo){
                    case KeyEvent.VK_UP:
                        player.setDy(0);
                        break;
                    case KeyEvent.VK_DOWN:
                        player.setDy(0);
                        break;
                    case KeyEvent.VK_LEFT:
                        player.setDx(0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setDx(0);
                        break;
                }
            }
        }
    }
}

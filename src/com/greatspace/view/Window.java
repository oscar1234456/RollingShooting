/**
 * @author: Derick Felix
 * @Data: 02/13/2016
 * @Release: 2.1
 * @Class: Window
 * @Objective: Create the Main Window
 */

package com.greatspace.view;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Window {

	public static JFrame frame= new JFrame("宇宙射擊");
    public Window() {

    	
        
            Game f = new Game();
         							
            f.checkPlayer();//定People number
        	//frame = new JFrame("宇宙射擊");
            frame.add(f);
            frame.setJMenuBar(f.criarMenu());
            frame.setDefaultCloseOperation(3); //3:The exit application default window close operation
            frame.setSize(500, 420);
            frame.setLocationRelativeTo(null); //the window is placed in the center of the screen
            frame.setResizable(false); //Can't control the size of frame
            try {
                URL sourceImage = Window.class.getResource("../sprites/gsicon.png"); //寫路徑
                BufferedImage image = ImageIO.read(sourceImage); //載icon圖檔
                frame.setIconImage(image); //定 frame icon
            } catch (IOException e) {
                System.out.println("圖片錯誤: " + e);
            }
            frame.setVisible(true);
        
        

        
    }

    public static void main(String[] args) {
       
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null, "警告"+ex+"\n介面風格將會以預設方式界定", "Warning", JOptionPane.WARNING_MESSAGE);
            //此處增加樣式錯誤的GUI介面
        }
        
            
        Window window = new Window(); //建立視窗類別
        System.out.println("Run!"); //控制台輸出成功開始的訊息
        

    }
    
    
}

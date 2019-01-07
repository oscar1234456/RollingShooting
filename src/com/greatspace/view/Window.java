/**\mainpage   捲軸射擊小遊戲  專題
 *  - \subpage  @author:110616038 陳泰元
 *  - \subpage @改寫自author: Derick Felix
 *  - \subpage @Release: 1.0
 *  - \subpage @Data: 12/14/2018
 */
 

package com.greatspace.view;



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;


/*
* @Class: Window
* @Objective: 創建Window物件
* 
*/
public class Window {

	public static JFrame frame= new JFrame("宇宙射擊"); //遊戲主畫面框架
	
	/**
	 * @brief 創建新遊戲的首要步驟
	 */
    public Window() { 

            Game gameCore = new Game(); //建構遊戲核心(JPanel)
         							
            gameCore.checkPlayer();//呼叫函式確認玩家人數
            frame.add(gameCore);
            frame.setJMenuBar(gameCore.createMenu());
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //設定為不要關閉視窗
            
            frame.setSize(500, 420);
            frame.setLocationRelativeTo(null); //將視窗的位置擺向螢幕中央
            frame.setResizable(false); //不允許使用者控制視窗縮放功能
            try {
                URL sourceImage = Window.class.getResource("../sprites/gsicon.png"); //取得圖片路徑物件
                BufferedImage image = ImageIO.read(sourceImage); //載入icon圖檔
                frame.setIconImage(image); //設定視窗icon圖示
            } catch (IOException e) {
                System.out.println("圖片錯誤: " + e); //萬一找不到時回報錯誤資訊
            }
            frame.setVisible(true);
            frame.addWindowListener(new win());

    }
    
    /**
	 * @brief 此處為程式開始點
	 * @param [in] String[] args
	 */
    public static void main(String[] args) {
       
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());//取得符合裝置平台的佈景樣式
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(null, "警告"+ex+"\n介面風格將會以預設方式界定", "Warning", JOptionPane.WARNING_MESSAGE);
            //此處增加樣式錯誤的GUI介面
        }
        
            
        new Window(); //建立視窗類別
        System.out.println("Run!"); //控制台輸出成功開始的訊息
        

    }
    
    class win extends WindowAdapter{
    	public void windowClosing(WindowEvent e){
    		int choose=1;
			//確認使用者是否真的要離開
			choose = JOptionPane.showConfirmDialog(frame, "您確定要離開?", "離開", 
					JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
			
			if(choose == 0) {
            	System.exit(0); //關閉程式
            }
    	}
    }
    
    
    
}

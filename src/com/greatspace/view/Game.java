package com.greatspace.view;

import com.greatspace.controller.Controller;
import com.greatspace.model.Bullet;
import com.greatspace.model.Enemy;
import com.greatspace.model.Player;
import com.greatspace.model.Music;
import com.greatspace.proxy.ProxyImage;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


import javax.swing.*;

/**
 * @author: Derick Felix
 * @Data: 02/13/2016
 * @Release: 2.1
 * @Class: Game
 * @Objective: Control the game
 */



/*
* @Class: Game
* @Objective: 創建遊戲核心(JPanel物件)
*/
public class Game extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Image gameBg; //儲存遊戲中背景圖
	private Image menuBg; //儲存選單背景圖
	
    private final Player playerSample; //玩家樣本物件
    private final Timer timer; //計時器
    private final Player player1; //玩家1
    private final Player player2; //玩家2
    
    private final int level1EnemyNum = 1;
    private final int level2EnemyNum = 2;
    private final int level3EnemyNum = 3;
    
    private int nowLevel = 1;
    
    //提示訊息(詢問遊戲人數使用)
    private JDialog frm ; 
	private JRadioButton rob1 ; 
	private JRadioButton rob2;
	private ButtonGroup bgroup ;
	private JButton btn1 ;
	private JButton btn2 ;
	
	private Music music;

    private boolean isP2 = false; //是否為雙人模式
    private boolean isInGame; //是否在遊戲中
    private boolean isInMenu; //是否在選單畫面狀態
    private boolean isWin; //是否贏了
    private boolean finalWin = false;
    
    private List<Enemy> enemyHouse; //儲存敵人的資料列

    /**
	 * @brief Game建構子
	*/
    public Game() {

    	music = new Music();
        this.playerSample = new Player();

        setFocusable(true); //為了聆聽keyListener 須保持Focus
        setDoubleBuffered(true);
        addKeyListener(new KeyBoardAdapter());

        //載入遊戲背景圖
        ImageIcon gameBgL = new ImageIcon(getClass().getResource("/com/greatspace/sprites/space.png"));
        gameBg = gameBgL.getImage();

        //由玩家樣本複製新玩家
        player1 = (Player) playerSample.clone();
        player1.setX(100);
        player1.setY(100);
        player1.setControle(Controller.PLAYER_1);

        player2 = (Player) playerSample.clone();
        player2.setX(100);
        player2.setY(200);
        player2.setControle(Controller.PLAYER_2);

        //遊戲狀態
        isInGame = false; //不在遊戲中
        isWin = false;//尚未贏局
        isInMenu = true;//在選單狀態中

        //初始化敵人
        initEnemy();

        //設定計時器並且以Game game當聆聽者隨時執行actionPerformed
        timer = new Timer(5, this); //緩衝延遲 5 sec
        timer.start(); //開始計時 
    }

    /**
	 * @brief 跳出視窗確認玩家人數
	*/
    public void checkPlayer() {
    	
    	frm = new JDialog(Window.frame,"選擇遊玩模式",true); //設定主控者並建立物件
    	
    	rob1 = new JRadioButton("單人遊玩");
    	rob2 = new JRadioButton("雙人遊玩");
    	bgroup = new ButtonGroup();
    	btn1 = new JButton("確認");
    	btn2 = new JButton("取消");
    	
    	frm.setLayout(null);
    	bgroup.add(rob2);
    	bgroup.add(rob1);
    	frm.add(rob1);
    	frm.add(rob2);
    	frm.add(btn1);
    	frm.add(btn2);
    	
    	//在此有兩項物件要使用相同程式碼故不使用Lambda運算式
    	btn1.addActionListener(new playerCheck()); 
    	btn2.addActionListener(new playerCheck());
    	
    	rob1.setSelected(true); //預設為單人遊戲
    	//設定尺寸及位置
    	rob1.setBounds(20,40,140,20);
    	rob2.setBounds(20,70,140,20);
    	btn1.setBounds(20,100,140,20);
    	btn2.setBounds(20,130,140,20);
    	frm.setSize(200, 200);
    	
    	frm.setLocationRelativeTo(Window.frame); //設定與遊戲視窗位置相關聯
    	frm.setResizable(false); //設定不可縮放視窗
    	frm.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); //設定關閉視窗按鍵無功效
    	
    	frm.setVisible(true);
    	frm.setModal(true); //設定擁有目前主控權
    }

    /**
	 * @brief 用於在Window物件中建立JMenuBar
	 * @param [out] JMenuBar 已經設計完成的JMenuBar物件
	*/
    public JMenuBar createMenu() {
        
        JMenuBar menuBar = new JMenuBar(); //建構MenuBar物件
        
        JMenu game = new JMenu("視窗");  //建構JMenu物件
       
        JMenuItem close = new JMenuItem("結束"); //建構JMenuItem物件
        
        close.addActionListener((ActionEvent e) -> {   //Lambda運算式 actionPerformed
        	int choose1;
			//確認使用者是否真的要離開
			choose1 = JOptionPane.showConfirmDialog(null, "您確定要離開?", "離開", 
					JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
			if(choose1 == 0) {
            	System.exit(0); //關閉程式
            }
        });
        
        game.add(close);

        JMenu help = new JMenu("幫助");

        JMenuItem about = new JMenuItem("關於");
        
        about.addActionListener((ActionEvent e) -> {	//Lambda運算式 actionPerformed
            JOptionPane.showMessageDialog(null, "<html><strong>宇宙射擊遊戲</strong><br><br> "
                    + "原作者: <strong>Derick Felix</strong>!<br>"
                    +"改善與改寫者: <strong>陳泰元</strong><br>"
                    +"本遊戲版權屬於原作者以及改寫者<br>"
                    +"如有侵權疑慮 <strong>敬請告知</strong>"
                    + "<br></html>", "About", 1);
        });
        
        JMenuItem htp = new JMenuItem("遊戲說明");
        htp.addActionListener((ActionEvent e) -> {	//Lambda運算式 actionPerformed
        	if(isP2==true) {
        		JOptionPane.showMessageDialog(null, "<html>"
        				+ "<strong>玩家 1</strong><br>"
                        + "發射子彈 - <strong>G</strong><br>"
                        + "控制往上 - <strong>W</strong><br>"
                        + "控制往下 - <strong>S</strong><br>"
                        + "控制向左 - <strong>A</strong><br>"
                        + "控制向右 - <strong>D</strong><br><br>"
                        + "<strong>玩家 2</strong><br>"
                        + "發射子彈 - <strong>Insert</strong><br>"
                        + "控制往上 - <strong>方向鍵 上</strong><br>"
                        + "控制往下 - <strong>方向鍵 下</strong><br>"
                        + "控制向左 - <strong>方向鍵 左</strong><br>"
                        + "控制向右 - <strong>方向鍵 右</strong><br>"
                        + "</html>", "雙人遊戲說明", JOptionPane.QUESTION_MESSAGE);
        	}else {
        		JOptionPane.showMessageDialog(null, "<html>"
        				+ "<br><strong>玩家 1</strong><br>"
                        + "發射子彈 - <strong>G</strong><br>"
                        + "控制往上 - <strong>W</strong><br>"
                        + "控制往下 - <strong>S</strong><br>"
                        + "控制向左 - <strong>A</strong><br>"
                        + "控制向右 - <strong>D</strong><br><br>"
                        + "</html>", "單人遊戲說明", JOptionPane.QUESTION_MESSAGE);
        	}
            
        });
        
        help.add(htp);
        help.add(about);
       
        menuBar.add(game);
        menuBar.add(help);
        
        return menuBar;
    }


    /**
	 * @brief 用於初始化敵人(包含數量,種類,出現位置,圖形邊界)
	*/
    private void initEnemy() {
        enemyHouse = new ArrayList<>();
        Enemy enemySample = new Enemy();//建構敵人樣本物件
        int enemyNumber = 0;
        
        //載入敵人圖片
        ProxyImage enemy1ImageL = new ProxyImage("/com/greatspace/sprites/enemy_1.gif");
        ProxyImage enemy2ImageL = new ProxyImage("/com/greatspace/sprites/enemy_2.gif");
        
        switch(nowLevel) {
        case 1:
        	enemyNumber = level1EnemyNum;
        	break;
        case 2:
        	enemyNumber = level2EnemyNum;
        	break;
        case 3:
        	enemyNumber = level3EnemyNum;
        	break;
        }
        
        for (int i = 0; i < enemyNumber; i++) {
        	
            Enemy ini = (Enemy) enemySample.clone();//複製敵人物件
            ini.setX(Enemy.GerarPosX()); //設定隨機生成位置(X軸)
          
            ini.setY(Enemy.GerarPosY()); //設定隨機生成位置(Y軸)

            if (i % 3 == 0) {
                ini.setImagem(enemy2ImageL.loadImage().getImage()); //設定為種類2的敵人圖片
            } else {
                ini.setImagem(enemy1ImageL.loadImage().getImage()); //設定為種類1的敵人圖片
            }

            //設立圖片邊界用以得知不可觸碰點
            ini.setAltura(ini.getImagem().getHeight(null)); 
            ini.setLargura(ini.getImagem().getWidth(null));

            ini.setVisivel(true);
            
            //ini.setVEL((int)(Math.random()*3));
            switch(nowLevel) {
            case 1:
            	if(i%5==0) {
                	ini.setVEL(1);
           		// System.out.println("setVEL");
           	 	}else if(i%2==0){
           	 		ini.setVEL(1.8); 
           	 	}else {
           	 		ini.setVEL(2);
           	 	}
            	break;
            case 2:
            	if(i%5==0) {
                	ini.setVEL(1);
           		// System.out.println("setVEL");
           	 	}else if(i%2==0){
           	 		ini.setVEL(2); 
           	 	}else {
           	 		ini.setVEL(2.3);
           	 	}
            	break;
            case 3:
            	if(i%5==0) {
                	ini.setVEL(1);
           		// System.out.println("setVEL");
           	 	}else if(i%2==0){
           	 		ini.setVEL(2.3); 
           	 	}else {
           	 		ini.setVEL(2.5);
           	 	}
            	break;
            }
            
            enemyHouse.add(ini);
            
        }
       
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D graficos = (Graphics2D) g;
        graficos.drawImage(gameBg, 0, 0, null); //預設先繪製gameBg背景圖
       
        if (isInGame) {

            if (player1.isMorto() == false) { //玩家還沒死
                graficos.drawImage(player1.getImagem(), player1.getX(), player1.getY(), this);
            }
            if (isP2 == true) {
                if (player2.isMorto() == false) {
                    ImageIcon player2_ = new ImageIcon(getClass().getResource("/com/greatspace/sprites/ship2.gif"));
                    player2.setImagem(player2_.getImage());
                    graficos.drawImage(player2.getImagem(), player2.getX(), player2.getY(), this);
                }
            }

            List<Bullet> misseis1 = player1.getMisseis();
            List<Bullet> misseis2 = player2.getMisseis();

            for (int i = 0; i < misseis1.size(); i++) {

                Bullet m = (Bullet) misseis1.get(i);
                graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);

            }
            for (int i = 0; i < misseis2.size(); i++) {

                Bullet m = (Bullet) misseis2.get(i);
                graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);

            }

            for (int i = 0; i < enemyHouse.size(); i++) {

                Enemy in = enemyHouse.get(i);
                graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);

            }
            if(enemyHouse.size()==1) {
            	 graficos.setColor(Color.RED);
            }else {
            	 graficos.setColor(Color.WHITE);
            }
            graficos.drawString("剩餘敵人: " + enemyHouse.size(), 5, 15);
            
            if(nowLevel == 1) {
           	 graficos.setColor(Color.BLUE);
           }else if(nowLevel == 2) {
           	 graficos.setColor(Color.ORANGE);
           }else {
        	 graficos.setColor(Color.GREEN);
           }
            graficos.drawString("目前級數: "+ nowLevel,5, 35);
            
        } else if (isWin) {
        		if(finalWin) {
        			ImageIcon isWinBg = new ImageIcon(getClass().getResource("/com/greatspace/sprites/game_won3.png"));
                    graficos.drawImage(isWinBg.getImage(), 0, 0, null);
                    music.finalWinStart();
                   
                    
        		}else {
        			if(nowLevel==2) {
        				ImageIcon isWinBg = new ImageIcon(getClass().getResource("/com/greatspace/sprites/game_won.png"));	
        				graficos.drawImage(isWinBg.getImage(), 0, 0, null);
        			}else if(nowLevel == 3) {
        				ImageIcon isWinBg = new ImageIcon(getClass().getResource("/com/greatspace/sprites/game_won2.png"));
        				graficos.drawImage(isWinBg.getImage(), 0, 0, null);
        			}
        			
        			
        			music.winStart();
        		}
            

        } else if (isInMenu) {
        	
        	
            ImageIcon bg_ = new ImageIcon(getClass().getResource("/com/greatspace/sprites/main_menu.png"));
            menuBg = bg_.getImage();
            graficos.drawImage(menuBg, 0, 0, null);

        } else {
           ImageIcon fimJogo = new ImageIcon(getClass().getResource("/com/greatspace/sprites/lose.png"));
            music.loseStart();
            graficos.drawImage(fimJogo.getImage(), 0, 0, null);
        }

        g.dispose(); //釋放系統資源 等待下次repaint

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    	//System.out.println("ac main");
    	if(isInMenu) {
    		music.bgmStart();
    	}else {
    		music.bgmStop();
    	}
    	if(isInGame) {
    		music.loseStop();
    		music.winStop();
    		music.finalWinStop();
    		music.bgmSpaceStart();
    	}else {
    		music.bgmSpaceStop();
    	}
    	
        if (enemyHouse.isEmpty()) {
            isInGame = false;
            isWin = true;
            if(nowLevel<3) {
            	nowLevel++;
                initEnemy();
            }else {
            	finalWin = true;
            	nowLevel = 0;

            	initEnemy();
            }
        }
        
        if(!isInGame&&!isWin) {
            	nowLevel=1;
                initEnemy();
        }
        
       

        List<Bullet> misseis1 = player1.getMisseis();
        List<Bullet> misseis2 = player2.getMisseis();

        for (int i = 0; i < misseis1.size(); i++) {

            Bullet m = (Bullet) misseis1.get(i);

            if (m.isVisivel()) {
                m.mexer();
            } else {
                misseis1.remove(i);
            }

        }
        for (int i = 0; i < misseis2.size(); i++) {

            Bullet m = (Bullet) misseis2.get(i);

            if (m.isVisivel()) {
                m.mexer();
            } else {
                misseis2.remove(i);
            }

        }

        for (int i = 0; i < enemyHouse.size(); i++) {

            Enemy in = enemyHouse.get(i);

            if (in.isVisivel()) {
            	if(enemyHouse.size()==1) {
            		in.setVEL(2.3);
            	}
                in.mexer();
            } else {
                enemyHouse.remove(i);
            }

        }

        player1.mexer();
        player2.mexer();
        checarColisoes();
        if (isP2 == true) {
            if (player1.isMorto() && player2.isMorto()) {

                isInGame = false;

            }
        }
        repaint();   
    }

    /**
   	 * @brief 檢查有無碰撞
   	*/
    public void checarColisoes() {

        Rectangle player1Area = player1.getBounds();
        Rectangle player2Area = player2.getBounds();
        Rectangle nowEnemy;
        Rectangle nowBullet;

        for (int i = 0; i < enemyHouse.size(); i++) {

        	//取得Enemy庫中的enemy
            Enemy tempEnemy = enemyHouse.get(i);
            //取得目前enemy的三角形
            nowEnemy = tempEnemy.getBounds();

            //檢查三角位置是否相交
            if (player1Area.intersects(nowEnemy)) {
                player1.setVisivel(false);
                player1.setMorto(true);//註記玩家1死亡
                //如果是單人模式則遊戲進入lose狀態
                if (isP2 == false) {
                    isInGame = false;
                }
            }
            if (player2Area.intersects(nowEnemy)) {
                player2.setVisivel(false);
                player2.setMorto(true);
            }
            if(isP2==true) {
            	//如果玩家1及玩家2都還有生命
                if (player1.isMorto() == false && player2.isMorto() == false) {
                	//以下是判斷兩人是否相交
                	//碰到時他們的力量會變弱
                    if (player1Area.intersects(player2Area)) {
                        player1.setDx(0);
                        player1.setDy(0);
                    }
                    if (player2Area.intersects(player1Area)) {
                        player2.setDx(0);
                        player2.setDy(0);
                    }
                }
            }
            

        }

        List<Bullet> misseis1 = player1.getMisseis();
        List<Bullet> misseis2 = player2.getMisseis();

        for (int i = 0; i < misseis1.size(); i++) {

            Bullet tempMissel = misseis1.get(i);
            nowBullet = tempMissel.getBounds();

            for (int j = 0; j < enemyHouse.size(); j++) {

                Enemy tempEnemy = enemyHouse.get(j);
                nowEnemy = tempEnemy.getBounds();

                if (nowBullet.intersects(nowEnemy)) {
                	music.bulletCrashStart();
                    tempEnemy.setVisivel(false);
                    tempMissel.setVisivel(false);

                }
                if(isP2==true) {
                	 if (nowBullet.intersects(player2Area)) {
                     	music.bulletCrashStart();
                        tempMissel.setVisivel(false);
                     }
                }
               

            }

        }
        for (int i = 0; i < misseis2.size(); i++) {

            Bullet tempMissel = misseis2.get(i);
            nowBullet = tempMissel.getBounds();

            for (int j = 0; j < enemyHouse.size(); j++) {

                Enemy tempEnemy = enemyHouse.get(j);
                nowEnemy = tempEnemy.getBounds();

                if (nowBullet.intersects(nowEnemy)) {
                    tempEnemy.setVisivel(false);
                    tempMissel.setVisivel(false);

                }
                if (nowBullet.intersects(player1Area)) {
                    tempMissel.setVisivel(false);
                }

            }

        }
    }

    /**
   	 * @brief 取得是否為雙人狀態
   	*/
    public boolean getisP2() {
        return this.isP2;
    }

    /**
   	 * @brief 繼承KeyAdapter改寫按鍵事件
   	*/
    private class KeyBoardAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) { //有按鍵按下時
            //如果按下Enter鍵
        	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            	System.out.println("tec enter");
            	
                if (isInGame == false) { //判別現在是否正在遊戲當中
                    //進來就表示不在遊戲中
                	isInGame = true; //使遊戲狀態改為開始
                    
                	//玩家狀態設定為 存活
                	player1.setMorto(false);
                    player2.setMorto(false);
                    isWin = false; //贏局狀態改為false
                    
                    if (isInMenu == true) { //離開選單畫面
                        isInMenu = false;
                    }
                    
                    if(finalWin) {
                    	finalWin = false;
                    }

                    //初始化玩家位置
                    player1.setX(100);
                    player1.setY(100);

                    player2.setX(100);
                    player2.setY(200);

                    //初始化敵人物件
                    initEnemy();
                    System.out.println("Enters");
                }
                
            }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //如果按下Esc鍵時
                isInGame = false; //將遊戲狀態改為不在遊戲中

                System.out.println("Esc");
                
            }else {			//如果不為Enter或Esc鍵時
        		player1.getControle().keyPressed(player1, e); //傳送按鍵類別給玩家1物件做後續控制
                if (isP2) { //如果是雙人遊戲
                    player2.getControle().keyPressed(player2, e); //傳送按鍵類別給玩家2物件做後續控制
                }
        	}

        }

        @Override
        public void keyReleased(KeyEvent e) {   //有按鍵放開時
        	System.out.println("keyPelease1 start");
        	
        	//先向玩家1類別取得控制器列舉
            player1.getControle().keyReleased(player1, e);//再傳送按鍵類別做後續控制
            if (isP2) {  //如果是雙人遊戲
                player2.getControle().keyReleased(player2, e);//傳送按鍵類別給玩家2物件做後續控制
            }
            
        }

    }
    
    /**
   	 * @brief 實作ActionListener的內部類別 改變遊戲人數
   	 */
    private class playerCheck implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		JButton btn = (JButton) e.getSource();
    		
    		if(btn == btn1) { //使用者按下確認時
    
    			if(rob1.isSelected()) {	 //使用者選擇單人遊玩時
    				 isP2 = false;
    				
    			}else {		//使用者選擇雙人遊玩時
    				isP2 = true;
    			}
    			frm.setVisible(false); //將Dialog視窗關閉
    			frm.setModal(false); //主控權交還Window.Frame
    			System.out.println(isP2);
    			
    		}else {		//使用者按下取消時
    			
    			int choose1;
    			
    			//確認使用者是否真的要離開
    			choose1 = JOptionPane.showConfirmDialog(null, "您確定要離開?", "離開", 
    					JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
               
    			if(choose1 == 0) {
                	frm.setModal(false); //主控權交還Window.Frame
                	System.out.println("Bye");
                	System.exit(0); //關閉程式
                }
    			
    		}
    	}
    	
    }
}

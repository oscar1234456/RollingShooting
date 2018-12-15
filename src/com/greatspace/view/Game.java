package com.greatspace.view;

import com.greatspace.controller.Controller;
import com.greatspace.model.Bullet;
import com.greatspace.model.Enemy;
import com.greatspace.model.Player;
import com.greatspace.proxy.ProxyImage;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
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
public class Game extends JPanel implements ActionListener {

    private int recp;
    private final Image fundo;
    private Image Inicio;
    private final Player nave;
    private final Timer timer;
    private final Player naveUm;
    private final Player naveDois;
    private JDialog frm ;
	private JRadioButton rob1 ;
	private JRadioButton rob2;
	private ButtonGroup bgroup ;
	private JButton btn1 ;
	private JButton btn2 ;

    private boolean p2 = false;
    private boolean emJogo;
    private boolean inicio;
    private boolean ganhoJogo;
   

    private List<Enemy> inimigos;

    public Game() {

        this.nave = new Player();

        setFocusable(true); //為了聆聽keyListener 須保持Focus
        setDoubleBuffered(true);
        addKeyListener(new TecladoAdapter());

        ImageIcon referencia = new ImageIcon(getClass().getResource("/com/greatspace/sprites/background.png"));
        fundo = referencia.getImage();

        naveUm = (Player) nave.clone();
        naveUm.setX(100);
        naveUm.setY(100);
        naveUm.setControle(Controller.PLAYER_1);

        naveDois = (Player) nave.clone();
        naveDois.setX(100);
        naveDois.setY(200);
        naveDois.setControle(Controller.PLAYER_2);

        //遊戲狀態
        emJogo = false; //是否在遊戲中
        ganhoJogo = false;//是否已經贏了
        inicio = true;//在選單狀態

        initEnemy();

        //設定計時器並且以Game game當聆聽者隨時執行actionPerformed
        timer = new Timer(5, this); //緩衝延遲 5 sec
        timer.start(); //開始計時 
    }

    public void checkPlayer() {
    	
    	frm = new JDialog(Window.frame,"選擇遊玩模式",true);
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
    	btn1.addActionListener(new playerCheck());
    	btn2.addActionListener(new playerCheck());
    	rob1.setSelected(true);
    	rob1.setBounds(20,40,140,20);
    	rob2.setBounds(20,70,140,20);
    	btn1.setBounds(20,100,140,20);
    	btn2.setBounds(20,130,140,20);
    	frm.setLocationRelativeTo(null);
    	frm.setResizable(false);
    	frm.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    	frm.setSize(200, 200);
    	frm.setVisible(true);
    	frm.setModal(true);
    }

    public JMenuBar createMenu() {
        
        JMenuBar menuBar = new JMenuBar(); //建構MenuBar物件
        
        JMenu game = new JMenu("視窗");  //建構JMenu物件
       
        JMenuItem close = new JMenuItem("結束"); //建構JMenuItem物件
        
        close.addActionListener((ActionEvent e) -> {   //匿名類別註冊事件
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
        
        about.addActionListener((ActionEvent e) -> {	//匿名類別註冊事件
            JOptionPane.showMessageDialog(null, "<html><strong>宇宙射擊遊戲</strong><br><br> "
                    + "原作者: <strong>Derick Felix</strong>!<br>"
                    +"改善與改寫者: <strong>陳泰元</strong><br>"
                    +"本遊戲版權屬於原作者以及改寫者<br>"
                    +"如有侵權疑慮 <strong>敬請告知</strong>"
                    + "<br></html>", "About", 1);
        });
        
        JMenuItem htp = new JMenuItem("遊戲說明");
        htp.addActionListener((ActionEvent e) -> {
        	if(p2==true) {
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

    private void initEnemy() {
        inimigos = new ArrayList<>();
        Enemy inimigo = new Enemy();//建構敵人樣本物件
        ProxyImage imagemInimigoUm = new ProxyImage("/com/greatspace/sprites/enemy_1.gif");
        ProxyImage imagemInimigoDois = new ProxyImage("/com/greatspace/sprites/enemy_2.gif");
        for (int i = 0; i < 1; i++) {
            Enemy ini = (Enemy) inimigo.clone();//複製敵人物件
            ini.setX(Enemy.GerarPosX());
            ini.setY(Enemy.GerarPosY());

            if (i % 3 == 0) {
                ini.setImagem(imagemInimigoDois.loadImage().getImage());
            } else {
                ini.setImagem(imagemInimigoUm.loadImage().getImage());
            }

            ini.setAltura(ini.getImagem().getHeight(null)); //設立圖片邊界用以得知不可觸碰點
            ini.setLargura(ini.getImagem().getWidth(null));

            ini.setVisivel(true);
            inimigos.add(ini);
            
        }
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D graficos = (Graphics2D) g;
        graficos.drawImage(fundo, 0, 0, null);

        if (emJogo) {

            if (naveUm.isMorto() == false) { //玩家還沒死
                graficos.drawImage(naveUm.getImagem(), naveUm.getX(), naveUm.getY(), this);
            }
            if (p2 == true) {
                if (naveDois.isMorto() == false) {
                    ImageIcon naveDois_ = new ImageIcon(getClass().getResource("/com/greatspace/sprites/ship2.gif"));
                    naveDois.setImagem(naveDois_.getImage());
                    graficos.drawImage(naveDois.getImagem(), naveDois.getX(), naveDois.getY(), this);
                }
            }

            List<Bullet> misseis1 = naveUm.getMisseis();
            List<Bullet> misseis2 = naveDois.getMisseis();

            for (int i = 0; i < misseis1.size(); i++) {

                Bullet m = (Bullet) misseis1.get(i);
                graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);

            }
            for (int i = 0; i < misseis2.size(); i++) {

                Bullet m = (Bullet) misseis2.get(i);
                graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);

            }

            for (int i = 0; i < inimigos.size(); i++) {

                Enemy in = inimigos.get(i);
                graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);

            }

            graficos.setColor(Color.WHITE);
            graficos.drawString("Enemies: " + inimigos.size(), 5, 15);

        } else if (ganhoJogo) {

            ImageIcon ganhojogo = new ImageIcon(getClass().getResource("/com/greatspace/sprites/game_won.png"));

            graficos.drawImage(ganhojogo.getImage(), 0, 0, null);
            

        } else if (inicio) {

            ImageIcon bg_ = new ImageIcon(getClass().getResource("/com/greatspace/sprites/main_menu.png"));
            Inicio = bg_.getImage();
            graficos.drawImage(Inicio, 0, 0, null);

        } else {
            ImageIcon fimJogo = new ImageIcon(getClass().getResource("/com/greatspace/sprites/game_over.png"));

            graficos.drawImage(fimJogo.getImage(), 0, 0, null);
        }

       // g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    	System.out.println("ac main");
        if (inimigos.isEmpty()) {
            emJogo = false;
            ganhoJogo = true;
        }

        List<Bullet> misseis1 = naveUm.getMisseis();
        List<Bullet> misseis2 = naveDois.getMisseis();

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

        for (int i = 0; i < inimigos.size(); i++) {

            Enemy in = inimigos.get(i);

            if (in.isVisivel()) {
                in.mexer();
            } else {
                inimigos.remove(i);
            }

        }

        naveUm.mexer();
        naveDois.mexer();
        checarColisoes();
        if (p2 == true) {
            if (naveUm.isMorto() && naveDois.isMorto()) {

                emJogo = false;

            }
        }
        repaint();
    }

    public void checarColisoes() {

        Rectangle formaNave1 = naveUm.getBounds();
        Rectangle formaNave2 = naveDois.getBounds();
        Rectangle formaInimigo;
        Rectangle formaMissel;

        for (int i = 0; i < inimigos.size(); i++) {

            Enemy tempInimigo = inimigos.get(i);
            formaInimigo = tempInimigo.getBounds();

            if (formaNave1.intersects(formaInimigo)) {
                naveUm.setVisivel(false);
                naveUm.setMorto(true);
                if (p2 == false) {
                    emJogo = false;
                }
            }
            if (formaNave2.intersects(formaInimigo)) {
                naveDois.setVisivel(false);
                naveDois.setMorto(true);
            }
            if (naveUm.isMorto() == false && naveDois.isMorto() == false) {
                if (formaNave1.intersects(formaNave2)) {
                    naveUm.setDx(0);
                    naveUm.setDy(0);
                }
                if (formaNave2.intersects(formaNave1)) {
                    naveDois.setDx(0);
                    naveDois.setDy(0);
                }
            }

        }

        List<Bullet> misseis1 = naveUm.getMisseis();
        List<Bullet> misseis2 = naveDois.getMisseis();

        for (int i = 0; i < misseis1.size(); i++) {

            Bullet tempMissel = misseis1.get(i);
            formaMissel = tempMissel.getBounds();

            for (int j = 0; j < inimigos.size(); j++) {

                Enemy tempInimigo = inimigos.get(j);
                formaInimigo = tempInimigo.getBounds();

                if (formaMissel.intersects(formaInimigo)) {

                    tempInimigo.setVisivel(false);
                    tempMissel.setVisivel(false);

                }
                if (formaMissel.intersects(formaNave2)) {

                    tempMissel.setVisivel(false);
                }

            }

        }
        for (int i = 0; i < misseis2.size(); i++) {

            Bullet tempMissel = misseis2.get(i);
            formaMissel = tempMissel.getBounds();

            for (int j = 0; j < inimigos.size(); j++) {

                Enemy tempInimigo = inimigos.get(j);
                formaInimigo = tempInimigo.getBounds();

                if (formaMissel.intersects(formaInimigo)) {

                    tempInimigo.setVisivel(false);
                    tempMissel.setVisivel(false);

                }
                if (formaMissel.intersects(formaNave1)) {

                    tempMissel.setVisivel(false);
                }

            }

        }
    }

    public boolean getP2() {
        return this.p2;
    }

    private class TecladoAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) { //有按鍵按下時
            //如果按下Enter鍵
        	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            	System.out.println("tec enter");
            	
                if (emJogo == false) { //判別現在是否正在遊戲當中
                    //進來就表示不在遊戲中
                	emJogo = true; //使遊戲狀態改為開始
                    
                	//玩家狀態設定為 存活
                	naveUm.setMorto(false);
                    naveDois.setMorto(false);
                    ganhoJogo = false; //贏局狀態改為false
                    
                    if (inicio == true) { //離開選單畫面
                        inicio = false;
                    }

                    //初始化玩家位置
                    naveUm.setX(100);
                    naveUm.setY(100);

                    naveDois.setX(100);
                    naveDois.setY(200);

                    //初始化敵人物件
                    initEnemy();
                    System.out.println("Enters");
                }
                
            }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //如果按下Esc鍵時
                emJogo = false; //將遊戲狀態改為不在遊戲中

                System.out.println("Esc");
                
            }else {			//如果不為Enter或Esc鍵時
        		naveUm.getControle().keyPressed(naveUm, e); //傳送按鍵類別給玩家1物件做後續控制
                if (p2) { //如果是雙人遊戲
                    naveDois.getControle().keyPressed(naveDois, e); //傳送按鍵類別給玩家2物件做後續控制
                }
        	}

        }

        @Override
        public void keyReleased(KeyEvent e) {   //有按鍵放開時
        	System.out.println("keyPelease1 start");
        	
            naveUm.getControle().keyReleased(naveUm, e);//傳送按鍵類別給玩家1物件做後續控制
            if (p2) {  //如果是雙人遊戲
                naveDois.getControle().keyReleased(naveDois, e);//傳送按鍵類別給玩家2物件做後續控制
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
    				 p2 = false;
    				
    			}else {		//使用者選擇雙人遊玩時
    				p2 = true;
    			}
    			frm.setVisible(false); //將Dialog視窗關閉
    			frm.setModal(false); //主控權交還Window.Frame
    			System.out.println(p2);
    			
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

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

        setFocusable(true);
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

        emJogo = false;
        ganhoJogo = false;
        inicio = true;

        initEnemy();

        timer = new Timer(5, this);
        timer.start();
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

    public JMenuBar criarMenu() {
        // Create a new MenuBar
        JMenuBar menub = new JMenuBar();
        // Create a new Menu
        JMenu game = new JMenu("Game");
        // Create a new Menu Item of Jogo menu
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        // Add fechar menu item to the jogo menu
        game.add(close);

        JMenu help = new JMenu("Help");

        JMenuItem about = new JMenuItem("About");
        about.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "<html><strong>Great Space</strong><br> "
                    + "Developed by <strong>Derick Felix</strong>!<br><br>"
                    + "<strong>What's new:</strong><br><br>"
                    + "- Bug Fixes <br>"
                    + "- Changes in game controler <br>"
                    + "<br></html>", "About", 1);
        });
        JMenuItem htp = new JMenuItem("How to Play");
        htp.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "<html>"
                    + "<strong>Player 1</strong><br>"
                    + "Fire - <strong>G</strong><br>"
                    + "Up - <strong>W</strong><br>"
                    + "Down - <strong>S</strong><br>"
                    + "Left - <strong>A</strong><br>"
                    + "Right - <strong>D</strong><br><br>"
                    + "<strong>Player 2</strong><br>"
                    + "Fire - <strong>Insert</strong><br>"
                    + "Up - <strong>UP ARROW</strong><br>"
                    + "Down - <strong>DOWN ARROW</strong><br>"
                    + "Left - <strong>LEFT ARROW</strong><br>"
                    + "Right - <strong>RIGHT ARROW</strong><br>"
                    + "</html>", "How to play", JOptionPane.QUESTION_MESSAGE);
        });
        // Add coj and sobre menu item to ajuda menu
        help.add(htp);
        help.add(about);
        // Add jogo and ajuda menu to the Menu Bar
        menub.add(game);
        menub.add(help);
        // Return the menu bar
        return menub;
    }

    private void initEnemy() {
        inimigos = new ArrayList<>();
        Enemy inimigo = new Enemy();
        ProxyImage imagemInimigoUm = new ProxyImage("/com/greatspace/sprites/enemy_1.gif");
        ProxyImage imagemInimigoDois = new ProxyImage("/com/greatspace/sprites/enemy_2.gif");
        for (int i = 0; i < 100; i++) {
            Enemy ini = (Enemy) inimigo.clone();
            ini.setX(Enemy.GerarPosX());
            ini.setY(Enemy.GerarPosY());

            if (i % 3 == 0) {
                ini.setImagem(imagemInimigoDois.loadImage().getImage());
            } else {
                ini.setImagem(imagemInimigoUm.loadImage().getImage());
            }

            ini.setAltura(ini.getImagem().getHeight(null));
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

            if (naveUm.isMorto() == false) {
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

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

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
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (emJogo == false) {
                    emJogo = true;
                    naveUm.setMorto(false);
                    naveDois.setMorto(false);
                    ganhoJogo = false;
                    if (inicio == true) {
                        inicio = false;
                    }

                    naveUm.setX(100);
                    naveUm.setY(100);

                    naveDois.setX(100);
                    naveDois.setY(200);

                    initEnemy();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                emJogo = false;
            }

            naveUm.getControle().keyPressed(naveUm, e);
            if (p2) {
                naveDois.getControle().keyPressed(naveDois, e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            naveUm.getControle().keyPressed(naveUm, e);
            if (p2) {
                naveDois.getControle().keyPressed(naveDois, e);
            }
        }

    }
    
    private class playerCheck implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		JButton btn = (JButton) e.getSource();
    		System.out.println("in");
    		if(btn == btn1) {
    
    			if(rob1.isSelected()) {
    				 p2 = false;
    				
    			}else {
    				p2 = true;
    			}
    			frm.setVisible(false);
    			frm.setModal(false);
    			System.out.println(recp);
    			
    		}else {
    			int choose1;
    			System.out.println("no");
    			choose1 = JOptionPane.showConfirmDialog(null, "您確定要離開?", "離開", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(choose1 == 0) {
                	frm.setModal(false);
                	System.out.println("BYe");
                	System.exit(0);
                }
    			
    		}
    	}
    	
    }
}

package com.greatspace.model;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * PROGRAMA DESENVOLVIDO POR DERICK FELIX.
 * DATA:13/02/2016 VERSAO: 2.1
 * CLASSE: INIMIGO
 * OBJETIVO: CRIAR OS ATRIBUTOS DOS INIMIGOS
 */
public class Enemy extends GameObject {

    private int x;
    private int y;

    private static final int LARGURA_TELA = 500;
    private static  int VELOCIDADE = 1;

    public Enemy() {
    }

    /**
	 * @brief 用於取得隨機的生成位置(X軸)
	*/
    public static int GerarPosX() {

        int aax = 456 + (int) (Math.random() * 1600);
        return aax;
    }

    /**
	 * @brief 用於取得隨機的生成位置(Y軸)
	*/
    public static int GerarPosY() {
        int aay = 10 + (int) (Math.random() * 320);

        return aay;
    }

    public void mexer() {
        if (this.x < 0) {
            this.x = GerarPosX();
            this.y = GerarPosY();
        } else {
            this.x -= VELOCIDADE;
        	//this.x -= 1;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBounds() {
    	//回傳三角形
        return new Rectangle(x, y, getLargura(), getAltura());
    }
    public void setVEL(int tempv) {
    	VELOCIDADE = tempv;
    }

}

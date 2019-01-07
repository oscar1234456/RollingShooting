package com.greatspace.model;

//import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

import com.greatspace.view.Window;

import javafx.embed.swing.JFXPanel;

public class Music {

	private MediaPlayer bgm;
	private MediaPlayer bgm_space;
	private MediaPlayer shoot1;
	private MediaPlayer shoot2;
	private MediaPlayer lose;
	private MediaPlayer win;
	private MediaPlayer bullet_crash;
	private MediaPlayer finalWin;
	
	private Media bgm_m;
	private Media bgm_space_m;
	private Media shoot1_m;
	private Media shoot2_m;
	private Media lose_m;
	private Media win_m;
	private Media bullet_crash_m;
	private Media finalWin_m;
	final JFXPanel fxPanel = new JFXPanel(); //為了初始化toolkit而使用
	
	public Music() {

		try {
			 
			  //File bgm_file = new File(Music.class.getResource("../sprites/bgm.mp3").toURI());
			  //System.out.println(bgm_file.toURI().toString());
			System.out.println(Music.class.getResource("../sprites/bgm.mp3").toURI().toString());
			URL sourceImage = Music.class.getResource("../sprites/bgm.mp3"); 
			bgm_m = new Media(sourceImage.toURI().toString());
			  bgm = new MediaPlayer(bgm_m);
			  
			}
			catch (Exception ex) {
			  System.out.println("bgm找不到");
			}
		
		try {
			 
			  //File shoot1_file = new File("src/com/greatspace/sprites/shoot1.mp3");
			  shoot1_m = new Media(Music.class.getResource("../sprites/shoot1.mp3").toURI().toString());
			  shoot1 = new MediaPlayer(shoot1_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("shoot1找不到");
			}
		
		try {
			  //File bgm_space_file = new File("src/com/greatspace/sprites/bgm_space.mp3");
			  bgm_space_m = new Media(Music.class.getResource("../sprites/bgm_space.mp3").toURI().toString());
			  bgm_space = new MediaPlayer(bgm_space_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("bgm_space找不到");
			}
		
		try {
			 // File shoot2_file = new File("src/com/greatspace/sprites/shoot2.mp3");
			  shoot2_m = new Media(Music.class.getResource("../sprites/shoot2.mp3").toURI().toString());
			  shoot2 = new MediaPlayer(shoot2_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("shoot2找不到");
			}
		
		try {
			 
			  //File lose_file = new File("src/com/greatspace/sprites/lose.mp3");
			  lose_m = new Media(Music.class.getResource("../sprites/lose.mp3").toURI().toString());
			  lose = new MediaPlayer(lose_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("lose找不到");
			}
		
		try {
			 
			 // File win_file = new File("src/com/greatspace/sprites/win.mp3");
			  win_m = new Media(Music.class.getResource("../sprites/win.mp3").toURI().toString());
			  win = new MediaPlayer(win_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("win找不到");
			}
		
		try {
			 
			  //File bullet_crash_file = new File("src/com/greatspace/sprites/bullet_crash.mp3");
			  bullet_crash_m = new Media(Music.class.getResource("../sprites/bullet_crash.mp3").toURI().toString());
			  bullet_crash = new MediaPlayer(bullet_crash_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("bullet_crash找不到");
			}
		
		try {
			 // File finalWin_file = new File("src/com/greatspace/sprites/finalWin.mp3");
			  finalWin_m = new Media(Music.class.getResource("../sprites/finalWin.mp3").toURI().toString());
			  finalWin = new MediaPlayer(finalWin_m);
			 
			}
			catch (Exception ex) {
			  System.out.println("finalWin找不到");
			}
	}
	
	public void bgmStart() {
		bgm.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         bgm.seek(Duration.ZERO);
		       }
		   });
		bgm.play();
	}
	
	public void bgmStop() {
		bgm.stop();
	}
	
	public void shoot1Start() {
		shoot1.setStopTime(Duration.millis(500));
		shoot1.play();
		shoot1.seek(Duration.millis(0));
	}
	
	public void bgmSpaceStart() {
		bgm_space.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         bgm_space.seek(Duration.ZERO);
		       }
		   });
		bgm_space.play();
	}
	
	public void bgmSpaceStop() {
		bgm_space.stop();
	}
	
	public void shoot2Start() {
		shoot2.setStopTime(Duration.millis(500));
		shoot2.play();
		shoot2.seek(Duration.millis(0));
	}
	
	public void loseStart() {
		lose.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         lose.seek(Duration.ZERO);
		       }
		   });
		lose.play();
	}
	
	public void loseStop() {
		lose.stop();
	}
	
	public void winStart() {
		win.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         win.seek(Duration.ZERO);
		       }
		   });
		win.play();
	}
	
	public void winStop() {
		win.stop();
	}
	
	public void bulletCrashStart() {
		bullet_crash.setStopTime(Duration.millis(500));
		bullet_crash.play();
		bullet_crash.seek(Duration.millis(0));
	}
	
	public void bulletCrashStop() {
		bullet_crash.stop();
	}
	
	public void finalWinStart() {
		finalWin.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         finalWin.seek(Duration.ZERO);
		       }
		   });
		finalWin.play();
	}
	
	public void finalWinStop() {
		finalWin.stop();
	}
	
}

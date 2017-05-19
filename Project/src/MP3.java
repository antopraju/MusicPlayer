
import java.io.BufferedInputStream;


import java.io.FileInputStream;
//import java.io.IOException;
import javazoom.jl.player.Player;
/* import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;*/


public class MP3 {
    private String filename;
    private Player player; 
    BufferedInputStream bis;
    int pausedOnFrame = 0;

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        this.filename = filename;
    }

    public void close() { 
    	if (player != null) player.close(); 
    	
    	int milli = player.getPosition();
		int seconds = (int) (milli / 1000) % 60 ;
    	System.out.println(seconds);
    
    }

    // play the MP3 file to the sound card
    public void play() {
        try {
            FileInputStream fis     = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }
 
	public void Pause() {
	/*	// TODO Auto-generated method stub
		player.close();
		AdvancedPlayer player = new AdvancedPlayer(bis);
		player.setPlayBackListener(new PlaybackListener() {
		    @Override
		    public void playbackFinished(PlaybackEvent event) {
		        pausedOnFrame = event.getFrame();
		    }
		});
		
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		int milli = player.getPosition();
		int seconds = (int) (milli / 1000) % 60 ;
    	System.out.println(seconds);
		
	}

/*
    // test client
    public static void main(String[] args) {
        //String filename = args[0];
    	 
        MP3 mp3 = new MP3("/home/anthony/Music/Hind Mere Jind - Sachin A Billion Dreams .mp3");
        mp3.play();
        try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         mp3.close();

    }*/

}


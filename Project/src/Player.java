public class Player {
	public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AudioPlayerTest("/home/anthony/Music/Aap.mp3").setVisible(true);
            }
        });
	}
}

import javax.sound.sampled.*;
 

public class Sound {

     

    private Clip clip;

     

    // Change file name to match yours, of course

    public static Sound sound = new Sound("music/bgmg.wav");
    public static Sound sound1 = new Sound("music/go.wav");
    public static Sound sound2 = new Sound("music/win.wav");


     

    public Sound (String fileName) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));

            clip = AudioSystem.getClip();

            clip.open(ais);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

 

    public void play() {

        try {

            if (clip != null) {

                new Thread() {

                    public void run() {

                        synchronized (clip) {

                            clip.stop();

                            clip.setFramePosition(0);

                            clip.start();

                        }

                    }

                }.start();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

  }

     

    public void stop(){

        if(clip == null) return;

        clip.stop();

    }

     

    public void loop() {

        try {

            if (clip != null) {

                new Thread() {

                    public void run() {

                        synchronized (clip) {

                            clip.stop();

                            clip.setFramePosition(0);

                            clip.loop(Clip.LOOP_CONTINUOUSLY);

                        }

                    }

                }.start();

           }

        } catch (Exception e) {

            e.printStackTrace();

       }

    }

     

    public boolean isActive(){

        return clip.isActive();

    }

}
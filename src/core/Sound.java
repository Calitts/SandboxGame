package core;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    
    private final Clip[] clips = new Clip[10];
    private final URL[] soundURL = new URL[10];

    public Sound() {
        // Certifique-se que os arquivos bird.wav e button.wav estão na pasta 'res' ou 'src'
        soundURL[0] = getClass().getResource("/bird.wav");
        soundURL[1] = getClass().getResource("/button.wav");    

        loadSound(0);
        loadSound(1);
    }

    private void loadSound(int i) {
        try {
            if (soundURL[i] != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clips[i] = AudioSystem.getClip();
                clips[i].open(ais);
            } else {
                System.out.println("ERRO: O arquivo de som índice " + i + " não foi encontrado!");
            }

        } catch (Exception e) {
            System.out.println("ERRO ao carregar som " + i);
            e.printStackTrace();
        }
    }    

    public void play(int i) {
        if (clips[i] == null) return;
        if (clips[i].isRunning()) clips[i].stop();
        clips[i].setFramePosition(0);
        clips[i].start();
    }

    public void loop(int i) {
        if (clips[i] == null) return;
        clips[i].setFramePosition(0);
        clips[i].loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(int i) {
        if (clips[i] != null) clips[i].stop();
    }
}
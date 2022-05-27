import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main implements Runnable{

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        SwingUtilities.invokeLater(new Main());
    }
    private final MusicPlayer player;

    public Main() {
        this.player = new MusicPlayer("src/tracks/track1.wav");
    }
    @Override
    public void run() {
        JFrame frame = new JFrame("Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createMainPanel(), BorderLayout.CENTER);
        frame.setSize(500, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        panel.setBackground(new Color(0, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        Font font = new Font("JetBrains Mono", Font.BOLD, 25);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> {
            player.start();
        });
        playButton.setFont(font);
        panel.add(playButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            player.stop();
        });
        pauseButton.setFont(font);
        panel.add(pauseButton);
        JButton replayButton = new JButton("Replay");
        replayButton.addActionListener(e -> {
            player.replay();
        });
        replayButton.setFont(font);
        panel.add(replayButton);

        return panel;
    }
    public class MusicPlayer {

        private final Clip track;

        public MusicPlayer(String filename) {
            this.track = openClip(filename);
        }
        private Clip openClip(String filename) {
            try {
                File filePath = new File(filename);
                AudioInputStream stream = AudioSystem.getAudioInputStream(filePath);
                Clip track = AudioSystem.getClip();
                track.open(stream);
                return track;
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            return null;
        }
        public void start() {
            if (track != null) {
                track.start();
            }
        }
        public void stop() {
            if (track != null) {
                track.stop();
            }
        }
        public void replay() {
            if (track != null) {
                track.setMicrosecondPosition(0);
            }
        }
    }
}


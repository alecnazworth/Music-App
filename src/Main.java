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
        frame.setSize(500, 400);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(175, 174, 174, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        Font font = new Font("JetBrains Mono", Font.BOLD, 25);

        // Panel for buttons: play, pause, replay
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBackground(Color.white);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        JPanel playButtonPanel = new JPanel(new BorderLayout());
        actionPanel.add(playButtonPanel, BorderLayout.CENTER);
        JPanel pauseButtonPanel = new JPanel(new BorderLayout());
        actionPanel.add(pauseButtonPanel, BorderLayout.WEST);
        JPanel replayButtonPanel = new JPanel(new BorderLayout());
        actionPanel.add(replayButtonPanel, BorderLayout.EAST);

        // Panel for track icon image
        JPanel iconPanel = new JPanel(new BorderLayout());
        mainPanel.add(iconPanel, BorderLayout.CENTER);
        JLabel trackIcon = new JLabel(new ImageIcon("src/resources/icons/track1Icon.png"));
        trackIcon.setSize(25, 25);
        iconPanel.add(trackIcon, BorderLayout.CENTER);

        // Buttons: play, pause, replay
        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> {
            player.start();
        });
        playButton.setFont(font);
        playButtonPanel.add(playButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            player.stop();
        });
        pauseButton.setFont(font);
        pauseButtonPanel.add(pauseButton);

        JButton replayButton = new JButton("Replay");
        replayButton.addActionListener(e -> {
            player.replay();
        });
        replayButton.setFont(font);
        replayButtonPanel.add(replayButton);

        return mainPanel;
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


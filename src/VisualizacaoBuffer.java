import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class VisualizacaoBuffer extends JFrame implements Runnable, ActionListener {
    private JPanel Programa;
    private JButton startP1Button;
    private JButton stopP1Button;
    private JButton startP2Button;
    private JButton stopP2Button;
    private JButton stopP3Button;
    private JButton startP3Button;
    private JButton startC1Button;
    private JButton stopC1Button;
    private JButton startC2Button;
    private JButton stopC2Button;
    public JLabel acaoProdutor1;
    public JLabel acaoProdutor2;
    public JLabel acaoProdutor3;
    public JLabel acaoConsumidor1;
    public JLabel acaoConsumidor2;
    public JLabel bufferAtual;

    public static volatile int[] buffer = {0, 0, 0, 0, 0};
    public static volatile boolean[] produtoresVivos = {true, true, true}, consumidoresVivos = {true, true};
    public static Semaphore semaforo = new Semaphore(5);

    public VisualizacaoBuffer() {
        criarComponentes();

        new Thread(new Produtor(1, semaforo, acaoProdutor1, bufferAtual), "Produtor 1").start();
        new Thread(new Produtor(2, semaforo, acaoProdutor2, bufferAtual), "Produtor 2").start();
        new Thread(new Produtor(3, semaforo, acaoProdutor3, bufferAtual), "Produtor 3").start();
        new Thread(new Consumidor(1, semaforo, acaoConsumidor1, bufferAtual), "Consumidor 1").start();
        new Thread(new Consumidor(2, semaforo, acaoConsumidor2, bufferAtual), "Consumidor 2").start();
    }

    private void criarComponentes() {
        startP1Button.addActionListener(this);
        startP1Button.setActionCommand("startP1");

        stopP1Button.addActionListener(this);
        stopP1Button.setActionCommand("stopP1");

        startP2Button.addActionListener(this);
        startP2Button.setActionCommand("startP2");

        stopP2Button.addActionListener(this);
        stopP2Button.setActionCommand("stopP2");

        startP3Button.addActionListener(this);
        startP3Button.setActionCommand("startP3");

        stopP3Button.addActionListener(this);
        stopP3Button.setActionCommand("stopP3");

        startC1Button.addActionListener(this);
        startC1Button.setActionCommand("startC1");

        stopC1Button.addActionListener(this);
        stopC1Button.setActionCommand("stopC1");

        startC2Button.addActionListener(this);
        startC2Button.setActionCommand("startC2");

        stopC2Button.addActionListener(this);
        stopC2Button.setActionCommand("stopC2");

        setContentPane(Programa);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void run() {
        if (!isVisible()) {
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "A janela já está aberta");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch (comando) {
            case "startP1" -> produtoresVivos[0] = true;
            case "stopP1" -> produtoresVivos[0] = false;
            case "startP2" -> produtoresVivos[1] = true;
            case "stopP2" -> produtoresVivos[1] = false;
            case "startP3" -> produtoresVivos[2] = true;
            case "stopP3" -> produtoresVivos[2] = false;
            case "startC1" -> consumidoresVivos[0] = true;
            case "stopC1" -> consumidoresVivos[0] = false;
            case "startC2" -> consumidoresVivos[1] = true;
            case "stopC2" -> consumidoresVivos[1] = false;
        }
    }
}

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Produtor implements Runnable {
    private final int idProdutor;
    private final Semaphore semaforo;
    private final JLabel label, bufferText;

    public Produtor(int idProdutor, Semaphore semaforo, JLabel acaoProdutor, JLabel buffer) {
        this.idProdutor = idProdutor;
        this.semaforo = semaforo;
        this.label = acaoProdutor;
        this.bufferText = buffer;
    }

    @Override
    public synchronized void run() {
        Random random = new Random();

        while (true) {
            if (VisualizacaoBuffer.produtoresVivos[idProdutor - 1]) {
                try {
                    semaforo.acquire();
                    int posicao = random.nextInt(5);
                    if (VisualizacaoBuffer.buffer[posicao] == 0) {
                        int quantidade = random.nextInt(10);
                        VisualizacaoBuffer.buffer[posicao] = quantidade;
                        setButtonText("Adicionou " + quantidade + " na posição " + (posicao + 1));
                    } else {
                        setButtonText("Posição " + (posicao + 1) + " já preenchida");
                    }
                    setBuffer();
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    semaforo.release();
                }
            }
        }
    }

    public void setButtonText(String texto) {
        this.label.setText(texto);
    }

    public void setBuffer() {
        int[] buffer = VisualizacaoBuffer.buffer;
        bufferText.setText("{ " + buffer[0] + ", " + buffer[1] + ", " + buffer[2] + ", " + buffer[3] + ", " + buffer[4] + "}");
    }
}

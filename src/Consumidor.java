import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Consumidor implements Runnable {
    private int idConsumidor;
    private Semaphore semaforo;
    private JLabel label, bufferText;

    public Consumidor(int idConsumidor, Semaphore semaforo, JLabel acaoConsumidor, JLabel buffer) {
        this.idConsumidor = idConsumidor;
        this.semaforo = semaforo;
        this.label = acaoConsumidor;
        this.bufferText = buffer;
    }

    @Override
    public synchronized void run() {
        Random random = new Random();

        while (true) {
            if(VisualizacaoBuffer.consumidoresVivos[idConsumidor - 1]) {
                try {
                    semaforo.acquire();
                    int posicao = random.nextInt(5);
                    if (VisualizacaoBuffer.buffer[posicao] != 0) {
                        setButtonText("Consumiu " + VisualizacaoBuffer.buffer[posicao] + " na posição " + (posicao + 1));
                        VisualizacaoBuffer.buffer[posicao] = 0;
                    } else {
                        setButtonText("Posição " + (posicao + 1) + " vazia");
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

    public void setButtonText(String texto){
        this.label.setText(texto);
    }

    public void setBuffer(){
        int[] buffer = VisualizacaoBuffer.buffer;
        bufferText.setText("{ " + buffer[0] + ", " + buffer[1] + ", " + buffer[2] + ", " + buffer[3] + ", " + buffer[4] + "}");
    }
}


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

    //Variáveis gerais
    public static JFrame frame;
    private final int WIDTH = 160;
    private final int HEIGHT = 90;
    private final int SCALE = 5;
    private Thread thread;
    private Boolean isRunning = true;
    private BufferedImage image;

    //Objetos
    Player player;

    //Método construtor (vai rodar uma única vez, quando for criado como objeto)
    public Game() {
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE)); //Define dimensões para o Canvas
        createFrame(); //Cria e cofigura a janela
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //Cria um Buffer para renderizar imagens
        
        new SpriteSheet(); //Adiciona a folha de sprites
        addKeyListener(this); //Adiciona o pressionamento de teclas
        player = new Player(30, 30); //Cria um jogador

    }

    //Cria e configura a janela
    public void createFrame() {
        frame = new JFrame("Nome da Janela"); //Cria uma Janela e da o nome dela
        frame.add(this); //Adiciona as dimensões e propriedades do canvas
        frame.setResizable(false); //Trava o tamanho da janela
        frame.pack(); //Empacota o Canvas com a janela, para calcular o tamanho dela
        frame.setLocationRelativeTo(null); //O janela vai ser criada no centro da tela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Quando o jogo fechar, o program tem que parar de rodar
        frame.setVisible(true); //Deixa a janela visivel
    }

    //Inicia o loop do jogo
    public synchronized void start() {
        thread = new Thread(this); //Cria uma nova tarefa
        isRunning = true; //O loop do jogo está ativado
        thread.start();
    }

    //Garante que o loop vai parar
    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game(); //Cria o jogo
        game.start(); //Inicia o jogo
    }

    //Lógica do Jogo (funcionalidades)
    public void tick() {
        player.tick();
    }

    //Gráficos do Jogo
    public void render() {
        BufferStrategy bs = this.getBufferStrategy(); //Buffers de ótimização de renderização
        if (bs == null) { //Se ele não existir, cria um e vaza
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics(); //Objetos para renderizar coisas na tela
        g.setColor(new Color(16, 16, 16)); //Define a cor do fundo
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE); //Denha um Fundo
        Graphics2D g2 = (Graphics2D) g; //Adiciona todos os métodos de Graphics2D

        /* RENDERIZAÇÃO DO JOGO */
        player.render(g2);
        /************************/

        //Desenha na tela de fato
        g.dispose(); //Método de otimização (limpa dados inúteis das imagens)
        g = bs.getDrawGraphics();  //Usa o Buffer de otimização      
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null); //Desenha tudo que foi definido nas linhas a cima
        bs.show(); //Mostrar tudo que for desenhado
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime(); //Tempo de referência super preciso do computador
        double framesPerSecond = 60.0; //Frames do jogo
        double nanoSec = 1000000000 / framesPerSecond; //Tempo de referencia para cada frame
        double delta = 0; //Variável de controle de frame
        double fps = 0; //Frames, só pra debugar a performance
        double fpsTimer = System.currentTimeMillis(); //Tempo menos preciso do computador
        while (isRunning) {
            long now = System.nanoTime(); //Tempo atual super preciso do computador
            delta += (now - lastTime) / nanoSec; //Intervalo de tempo para cada frame
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }

            //FPS debug
            if ((System.currentTimeMillis()-fpsTimer)  >= 1000) {
                System.out.println("FPS: " + fps);
                fps = 0;
                fpsTimer += 1000;
            }
        }

        stop(); //Garante que vai parar a execução dos processos do jogo caso saia do loop
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}

import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle {

    //private BufferedImage sprite;

    public Player(int x, int y) {
        super(x, y, 9, 12); //Manda para o retangulo de colisão, as possições do jogador
    }

    //Lógica
    public void tick() {
        
    }
    
    //Gráficos
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.jogador, x, y, width, height, null);
    }
}

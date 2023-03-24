import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    
    public static BufferedImage spriteSheet;
    public static BufferedImage jogador;
    private String path;
    
    public SpriteSheet() {
        path = "res/Sprites.png"; //Caminho da folha de sprites que contém todos os gráficos do jogo
        try {
            spriteSheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        jogador = SpriteSheet.getSprite(0, 0, 9, 12); //Define o primeiro sprite do jogador
    }

    public static BufferedImage getSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }
}

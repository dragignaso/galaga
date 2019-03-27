import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Galaga extends JFrame{
private Image image;
private Graphics graphics;

Image mapa = new ImageIcon("mapa.jpg").getImage();
Image prota = new ImageIcon("spriteprotat.png").getImage();
Image enemigo = new ImageIcon("spriteenemigo.png").getImage();
//Image bala = new ImageIcon("ataque.png").getImage();

//prota
int x;
int y = 550;
int healt = 500;

//enemigos
Random aX = new Random();
Random aY = new Random();
int enemigoX = aX.nextInt(700);
int enemigoY = aY.nextInt(500);
int enemigoHealth = 100;

//balas
int shoting = 0;
int bX = 800;
int bY;

    public Galaga(){
        super();
        Handler keys = new Handler();
        addKeyListener(keys);
    }

    private class Handler implements KeyListener{
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT){
                x = x + 50;
            }else if(keyCode == KeyEvent.VK_LEFT){
                x = x - 50;
            }else if(keyCode == KeyEvent.VK_SPACE){
                shoting = 1;
            }else if(keyCode == KeyEvent.VK_UP){
                if(y >50){
                    y = y - 50;
                }
            }else if(keyCode == KeyEvent.VK_DOWN){
                if(y < 550){
                    y = y + 50;
                }
            }
        }
        public void keyTyped(KeyEvent e){

        }
        public void keyReleased(KeyEvent e){

        }
    }

    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        paintComponents(graphics);
        g.drawImage(image,0,0,this);
    }

    public void paintComponents(Graphics g){
        //mapa
        g.drawImage(mapa, 0, 0, 800, 600, null);

        //enemigo
        if (enemigoHealth > 0){
            g.drawImage(enemigo,enemigoX,enemigoY,50,50,null);
            if (x < enemigoX){
                enemigoX = enemigoX - 1;
            }else if(x > enemigoX){
                enemigoX = enemigoX + 1;
            }
        }
        
        //balas
        g.setColor(Color.WHITE);
        if(shoting != 0){
            g.fillRect(bX, bY, 20, 20);
            bY = bY-10;
            if(bX > enemigoX && bX < enemigoX + 100 && bY > enemigoY && bY < enemigoY + 100){
                enemigoHealth = enemigoHealth - 100;
            }
        }
        g.fillRect(bX, bY, 20, 20);
        
        //protagonista
        g.drawImage(prota,x,y,50,50,null);
        repaint();

    }

    public static void main(String args[]){
        Galaga juego = new Galaga();
        juego.setVisible(true);
        juego.setSize(800,600);
    }
}
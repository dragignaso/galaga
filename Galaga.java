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
Image vida = new ImageIcon("swigt.png").getImage();
Image retrato = new ImageIcon("rostroprotat.png").getImage();
Image gameOver = new ImageIcon("gameover.png").getImage();

//prota
int x = 400;
int y = 550;
int healt = 500;
int vidas = 2;

//enemigo
Random aX = new Random();
Random aY = new Random();
int enemigoX = aX.nextInt(700);
int enemigoY = 25;
int enemigoHealth = 100;

//balas
int shoting = 0;
int bX = 800;
int bY;

//sistema de juego
int score = 0;
String cadena = "Lagrimas de alumnos: ";
int velocidad = 80;
int puntajeTopeNivelAnt = 0;
int puntajeProximoNivel = 1500;
int nivel = 1;

    public Galaga(){
        super();
        Handler keys = new Handler();
        addKeyListener(keys);
    }

    private class Handler implements KeyListener{
        public void keyPressed(KeyEvent e){
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT){
                if(x < 750){
                    x = x + 50;
                }
            }else if(keyCode == KeyEvent.VK_LEFT){
                if(x > 0){
                    x = x - 50;
                }
            }else if(keyCode == KeyEvent.VK_SPACE){
                bX = x + 12;
                bY = y ;
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

        //marcador
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahoma", Font.BOLD,18));
        g.drawString("Lagrimas de", 665, 500);
        g.drawString("alumnos", 680, 525);
        g.drawString(Integer.toString(score), 695, 550);
        g.drawString("Nivel: " + Integer.toString(nivel), 680, 470);

        //enemigo
        if (enemigoHealth > 0 && enemigoY < 600){
            g.drawImage(enemigo,enemigoX,enemigoY,50,50,null);

            if (aY.nextInt(100) > velocidad){
                enemigoY = enemigoY + 1;
            }
        }
        if (enemigoHealth > 0 &&enemigoY >= 600){
            enemigoX = aX.nextInt(700);
            enemigoY = 25;
            vidas = vidas -1;

            //prota
            x = 400;
            y = 550;
            
            //balas
            shoting = 0;
            bX = 800;
            bY = 600;
        }
        
        //balas
        if(shoting != 0 && bY < 0){
            shoting = 0;
        }
        g.setColor(Color.RED);
        if(shoting == 1){
            g.fillRect(bX, bY, 20, 20);
            bY = bY-3;
        }
        if(bX > enemigoX && bX < enemigoX + 50 && bY > enemigoY && bY < enemigoY + 50){
            enemigoHealth = enemigoHealth - 100;
            shoting = 0;
            score = score + 50;

            enemigoX = aX.nextInt(700);
            enemigoY = 25;
            enemigoHealth = 100;
            bY = 600;
            bX = 800;

            //una vida extra cada 1000 puntos
            if(score % 750 == 0){
                vidas = vidas + 1;
            }
            if(score == puntajeProximoNivel){
                if (puntajeTopeNivelAnt == 0){
                    puntajeTopeNivelAnt = puntajeProximoNivel;
                    puntajeProximoNivel = puntajeProximoNivel * 2;
                }else{
                    int temp = 0;
                    temp = puntajeTopeNivelAnt;
                    puntajeTopeNivelAnt = puntajeProximoNivel;
                    puntajeProximoNivel = puntajeTopeNivelAnt + temp;
                }
                
                nivel = nivel + 1;
                velocidad = velocidad - 5;
            }
        }
        g.fillRect(bX, bY, 20, 20);
        
        //protagonista
        g.drawImage(prota,x,y,50,50,null);

        if(enemigoX > x && enemigoX < x + 50 && enemigoY > y && enemigoY < y + 50){
            healt = enemigoHealth - 500;
            vidas = vidas -1;
            //prota
            x = 400;
            y = 550;
            healt = 500;

            //enemigos
            enemigoX = aX.nextInt(700);
            enemigoY = 25;
            enemigoHealth = 100;

            //balas
            shoting = 0;
            bX = 800;
            bY = 600;
        }

        //vidas
        g.drawImage(retrato,600,30,65,65,null);
        if (vidas >= 3){
            g.drawImage(vida, 750, 30, 30, 30, null);
        }
        if (vidas >= 2){
            g.drawImage(vida, 710, 30, 30, 30, null);
        }
        if (vidas >= 1){
            g.drawImage(vida, 670, 30, 30, 30, null);
        }
        if (vidas >= 6){
            g.drawImage(vida, 750, 65, 30, 30, null);
        }
        if (vidas >= 5){
            g.drawImage(vida, 710, 65, 30, 30, null);
        }
        if (vidas >= 4){
            g.drawImage(vida, 670, 65, 30, 30, null);
        }
        if(vidas < 1){
            g.drawImage(gameOver, 0, 0, 800, 600, null);
        }
        
        repaint();
    }

    public static void main(String args[]){
        Galaga juego = new Galaga();
        juego.setVisible(true);
        juego.setSize(800,600);
    }
}
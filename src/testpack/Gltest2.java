package testpack;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.*;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;

public class Gltest2 implements GLEventListener, MouseListener, KeyListener
{
	public static void main(String[] args){
		new Gltest2();
	}

	float[][] vertex = {
			{ 0.0f, 0.0f, 0.0f}, /* A */
			{ 1.0f, 0.0f, 0.0f}, /* B */
			{ 1.0f, 1.0f, 0.0f}, /* C */
			{ 0.0f, 1.0f, 0.0f}, /* D */
			{ 0.0f, 0.0f, 1.0f}, /* E */
			{ 1.0f, 0.0f, 1.0f}, /* F */
			{ 1.0f, 1.0f, 1.0f}, /* G */
			{ 0.0f, 1.0f, 1.0f} /* H */
			};

			int[][] edge = {
			{ 0, 1}, /* ア (A-B) */
			{ 1, 2}, /* イ (B-C) */
			{ 2, 3}, /* ウ (C-D) */
			{ 3, 0}, /* エ (D-A) */
			{ 4, 5}, /* オ (E-) */
			{ 5, 6}, /* カ (-G) */
			{ 6, 7}, /* キ (G-H) */
			{ 7, 4}, /* ク (H-E) */
			{ 0, 4}, /* ケ (A-E) */
			{ 1, 5}, /* コ (B-) */
			{ 2, 6}, /* サ (C-G) */
			{ 3, 7} /* シ (D-H) */
			};

			private final GLU glu;
			private final Animator animator; //(1)
			private final GLWindow glWindow;
			private boolean willAnimatorPause = false;
			private static final char KEY_ESC = 0x1b;

			//回転角
			float r = 0;

			public Gltest2() {
				GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
				glu = new GLU();

				glWindow = GLWindow.create(caps);
				glWindow.setTitle("Cube demo (Newt)");
				glWindow.setSize(300, 300);
				glWindow.addGLEventListener(this);

				glWindow.addWindowListener(new WindowAdapter() {
					@Override
					public void windowDestroyed(WindowEvent evt) {
						System.exit(0);
					}
				});

				glWindow.addMouseListener(this);
				glWindow.addKeyListener(this);
				animator = new Animator(); //(2)
				animator.add(glWindow); //(3)
				animator.start(); //(4)
				animator.pause(); //(5)
				glWindow.setVisible(true);
			}
			@Override
			public void init(GLAutoDrawable drawable) {
				GL2 gl = drawable.getGL().getGL2();
				//背景を白く塗りつぶす.
				gl.glClearColor(1f, 1f, 1f, 1.0f);
			}

			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
				GL2 gl = drawable.getGL().getGL2();

				gl.glMatrixMode(GL_PROJECTION);
				gl.glLoadIdentity();
				glu.gluPerspective(30.0, (double)width / (double)height, 1.0, 300.0);

				// 視点位置と視線方向
				glu.gluLookAt(3.0f, 4.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
				gl.glMatrixMode(GL_MODELVIEW);
			}

			@Override
			public void display(GLAutoDrawable drawable) {
				GL2 gl = drawable.getGL().getGL2();
				gl.glClear(GL.GL_COLOR_BUFFER_BIT);

				gl.glLoadIdentity();

				// 図形の回転
				gl.glRotatef(r, 0.0f, 1.0f, 0.0f); //(6)
				// 図形の描画
				gl.glColor3f(0.0f, 0.0f, 0.0f);
				gl.glBegin(GL_LINES);
				for (int i = 0; i < 12; i++) {
					gl.glVertex3fv(vertex[edge[i][0]], 0);
					gl.glVertex3fv(vertex[edge[i][1]], 0);
				}
				gl.glEnd();

				//一周回ったら回転角を 0 に戻す
				if (r++ >= 360.0f) r = 0;
				System.out.println("anim:" + animator.isAnimating() + ", r:" + r);
				if(willAnimatorPause) {
					animator.pause(); //(8)
					System.out.println("animoator paused:");
					willAnimatorPause = false;
				}
			}

			@Override
			public void dispose(GLAutoDrawable drawable) {
				if(animator != null) animator.stop();
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if(keyChar == KEY_ESC || keyChar == 'q' || keyChar == 'Q') {
					glWindow.destroy();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) { }

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				switch(e.getButton()) {
					case MouseEvent.BUTTON1:
						animator.resume(); //(9)
						System.out.println("button 1, left click");
						break;
					case MouseEvent.BUTTON2:
						System.out.println("button 2");
						break;
					case MouseEvent.BUTTON3:
						System.out.println("button 3, right click");
						willAnimatorPause = true; //(10)
						animator.resume(); //(11)
						break;
					default:
						//empty
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				animator.pause();
			}

			@Override
			public void mouseMoved(MouseEvent e) { }

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseWheelMoved(MouseEvent e) {}
}

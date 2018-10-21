package testpack;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

public class gltest implements GLEventListener
{ //(1)

	public static void main(String[] args) {
		new gltest();
	}

	private Animator animator;
	private float[] colors;
	private final short linePattern = 0b111100011001010;

	public gltest() {
		initColors();
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));//(2)
		GLWindow glWindow = GLWindow.create(caps); //(3)
		glWindow.setTitle("First demo (Newt)"); //(4)タイトル設定
		glWindow.setSize(300, 300); //(5)ウィンドウサイズ設定

		glWindow.addWindowListener(new WindowAdapter() { //(6)タイトルバー上のクローズアイコンをクリックするとアプリ終了の定義
			@Override
			public void windowDestroyed(WindowEvent evt) {
				System.exit(0);
			}
		});
		glWindow.addGLEventListener(this); /*(7)どのクラスがJOGLからのイベントを受け取るのかを定義している。
		 									ここではgltestクラスが処理するように定義している*/
		Animator animator = new Animator(); //(8)
		animator.add(glWindow);
		animator.start();
		//glWindow.setPosition(500,500);//(9)ウィンドウの位置の設定
		glWindow.setVisible(true); //(10)ウィンドウが見えるようにする。
	}
	private void initColors() {
		colors = new float [8];
		for(int i = 0; i < 8 ; i++) {
			colors[i] = 0.3f + (0.1f*i);
		}
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();//windowを青く塗る
		gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);//メソッド(引数はfloat型、1.0だけだとエラーになる)一度だけでok
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

	@Override
	public void display(GLAutoDrawable drawable) {
		@SuppressWarnings("unused")
		final GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);//ウィンドウの塗りつぶし
		for (int i = 0 ; i < 8 ; i++) {
			gl2.glPointSize((i+1)*0.5f);//(2)
			gl2.glColor3f(1.0f, 1.0f, 1.0f);
			gl2.glBegin(GL.GL_POINTS);//3
			gl2.glVertex2f(-0.9f, (i-7)*(1.6f/7f)+0.8f);
			gl2.glEnd();
		}
		for (int i = 0 ; i < 8 ; i++) {
			gl2.glPointSize(2f);
			gl2.glBegin(GL.GL_POINTS);
			gl2.glColor3f(colors[i],colors[i],colors[i]);
			gl2.glVertex2f(-0.8f, (i-7)*(1.6f/7f)+0.8f);
			gl2.glEnd();
		}
		for(int i = 1; i < 9; i++) {
			gl2.glLineWidth(i * 0.5f); //(4)
			gl2.glColor3f(1.0f, 1.0f, 1.0f);
			gl2.glBegin(GL.GL_LINES);
			gl2.glVertex2f(-0.6f + i*0.05f, -0.8f);
			gl2.glVertex2f(-0.6f + i*0.05f, +0.8f);
			gl2.glEnd();
		}
		gl2.glLineWidth(1f);
		for(int i = 0; i < 8; i++) {
			gl2.glColor3f(colors[i], colors[i], colors[i]);
			gl2.glBegin(GL.GL_LINES);
			gl2.glVertex2f(-0.1f + i*0.05f, -0.8f);
			gl2.glVertex2f(-0.1f + i*0.05f, +0.8f);
			gl2.glEnd();
		}
		gl2.glEnable(GL.GL_LINE_STRIP);
		for(int i = 0; i < 8; i++) {
			gl2.glLineStipple(i+1, linePattern); //(6)
			gl2.glColor3f(colors[i], colors[i], colors[i]);
			gl2.glBegin(GL.GL_LINES);
			gl2.glVertex2f(+0.4f + i*0.05f, -0.8f);
			gl2.glVertex2f(+0.4f + i*0.05f, +0.8f);
			gl2.glEnd();
		}
		gl2.glDisable(GL.GL_LINE_STRIP);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		if(animator != null) animator.stop();
	}
}
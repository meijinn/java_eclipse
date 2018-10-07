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

	public gltest() {
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
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);//ウィンドウの塗りつぶし
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2f(-0.9f,-0.9f);
		gl.glVertex2f(0.9f, -0.9f);
		gl.glVertex2f(0.9f, 0.9f);
		gl.glVertex2f(-0.9f, 0.9f);
		gl.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		if(animator != null) animator.stop();
	}
}
package takahiro.surfaceview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by takahiro on 2016/01/31.
 */
public class SurfaceViewView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder holder;
    private Thread thread;

    private Bitmap image;
    private int px = 0; // x座標
    private int py = 0; // y座標
    private int vx = 6; // x速度
    private int vy = 6; // y速度

    public SurfaceViewView(Context context) {
        super(context);

        // 画像の読み込み
        Resources r = getResources();
        image = BitmapFactory.decodeResource(r, R.drawable.a0120000005);

        // サーフェイスホルダーの作成
        holder = getHolder();
        holder.addCallback(this);

    }

    // サーフェイスの生成
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // スレッドの生成
        thread = new Thread(this);
        thread.start();

    }

    // サーフェイスの変更
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // ループ処理
    @Override
    public void run() {
        while(thread != null) {
            long nextTime = System.currentTimeMillis() + 30;
            onTick();
            try {
                Thread.sleep(nextTime-System.currentTimeMillis());
            } catch(Exception e) {

            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;

    }

    // 定期処理
    private void onTick() {
        // ダブルバッファリング
        Canvas canvas = holder.lockCanvas();
        if(canvas == null)return;
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(image, px - 120, py - 120, null);
        holder.unlockCanvasAndPost(canvas);

        // 移動
        if(px < 0 || getWidth() < px) vx = -vx;
        if(py < 0 || getHeight() < py) vy = -vy;
        px += vx;
        py += vy;
    }
}

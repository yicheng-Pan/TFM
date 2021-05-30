package yicheng.pan.tfm.Scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import yicheng.pan.tfm.R;
import yicheng.pan.tfm.Util.ImageUtil;


public class QrcActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;
    private ImageView iv_sg;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrc);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

        initView();
    }


    public static boolean isOpen = false;

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear1);
        LinearLayout ll_qrc = (LinearLayout) findViewById(R.id.ll_qrc);
        iv_sg= (ImageView) findViewById(R.id.iv_sg);
        ll_qrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 选择系统图片并解析
         */
         if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Intent resultIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                            bundle.putString(CodeUtils.RESULT_STRING, result);
                            resultIntent.putExtras(bundle);
                            yicheng.pan.tfm.Scan.QrcActivity.this.setResult(RESULT_OK, resultIntent);
                            yicheng.pan.tfm.Scan.QrcActivity.this.finish();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(yicheng.pan.tfm.Scan.QrcActivity.this, "Fail", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            yicheng.pan.tfm.Scan.QrcActivity.this.setResult(RESULT_OK, resultIntent);
            yicheng.pan.tfm.Scan.QrcActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            yicheng.pan.tfm.Scan.QrcActivity.this.setResult(RESULT_OK, resultIntent);
            yicheng.pan.tfm.Scan.QrcActivity.this.finish();
        }
    };

}

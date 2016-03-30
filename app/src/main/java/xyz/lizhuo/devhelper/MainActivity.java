package xyz.lizhuo.devhelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_WRITE_SETTINGS = 2;
  private int screenTimeOut = 15000;
  private Utils utils;

  @Bind(R.id.adbWifiEnable) TextView wifi;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    utils = new Utils();
  }

  @TargetApi(Build.VERSION_CODES.M) @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
      if (Settings.System.canWrite(this)) {
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
            screenTimeOut);
      } else {

      }
    }
  }

  @TargetApi(Build.VERSION_CODES.M) @OnClick({
      R.id.adbWifiEnable, R.id.debugLayoutEnable, R.id.debugLayoutDisable,
      R.id.showGPUBarEnable, R.id.showGPUBarDisable, R.id.setNoClose, R.id.nonal
  }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.adbWifiEnable:
        utils.startWifiAdb(true);
        wifi.setText("ip address : " + utils.getIpAddress(this));
        break;
      case R.id.debugLayoutEnable:
        utils.setDebugLayout(true);
        break;
      case R.id.debugLayoutDisable:
        utils.setDebugLayout(false);
        break;
      case R.id.showGPUBarEnable:
        utils.setGPULineBar(true);
        break;
      case R.id.showGPUBarDisable:
        utils.setGPULineBar(false);
        break;
      case R.id.overDraw:
        utils.setOverDraw(true);
        break;
      case R.id.setNoClose: {
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
          }else{
            screenTimeOut=2147483647;
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
              screenTimeOut);
          }
        }
        break;
      case R.id.nonal:{
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
          }else{
            screenTimeOut=15000;
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
              screenTimeOut);
          }
        }
        break;
    }
  }
}

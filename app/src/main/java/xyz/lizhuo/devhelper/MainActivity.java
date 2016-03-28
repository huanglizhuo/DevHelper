package xyz.lizhuo.devhelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_WRITE_SETTINGS = 2;
  private int screenTimeOut = 15000;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  //@TargetApi(Build.VERSION_CODES.M) @OnClick(R.id.setNoClose) public void sconOnClick() {
  //  if (!Settings.System.canWrite(this)) {
  //    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
  //        Uri.parse("package:" + getPackageName()));
  //    startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
  //  }
  //  Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 2147483647);
  //}

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
      R.id.adbWifiEnable, R.id.adbWifiDisable, R.id.debugLayoutEnable, R.id.debugLayoutDisable,
      R.id.showGPUBarEnable, R.id.showGPUBarDisable, R.id.setNoClose, R.id.nonal
  }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.adbWifiEnable:
        Utils.startWifiAdb(true);
        break;
      case R.id.adbWifiDisable:
        Utils.startWifiAdb(false);
        break;
      case R.id.debugLayoutEnable:
        Utils.setDebugLayout(true);
        break;
      case R.id.debugLayoutDisable:
        Utils.setDebugLayout(false);
        break;
      case R.id.showGPUBarEnable:
        Utils.setGPULineBar(true);
        break;
      case R.id.showGPUBarDisable:
        Utils.setGPULineBar(false);
        break;
      case R.id.setNoClose: {
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
          }else{
            screenTimeOut=2147483647;
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
          }
        }
        break;
    }
  }
}

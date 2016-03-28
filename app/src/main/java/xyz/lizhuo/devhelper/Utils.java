package xyz.lizhuo.devhelper;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lizhuo on 16/3/27.
 */
public class Utils {

  public static String  getIpAddress(Context context){
    int ipAddress=0;
    WifiManager wifiManager = (WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE );
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    ipAddress = wifiInfo.getIpAddress();
    return (ipAddress & 0xFF ) + "." +
        ((ipAddress >> 8 ) & 0xFF) + "." +
        ((ipAddress >> 16 ) & 0xFF) + "." +
        ( ipAddress >> 24 & 0xFF) ;
  }

  public static void startWifiAdb(boolean enable){
    execShell(new String[]{"setprop service.adb.tcp.port "+(enable? "5555":"-1 \n" ),"stop adbd\n","start adbd\n"});
  }

  public static void setDebugLayout(boolean show){
    execShell(new String[]{"setprop debug.layout "+(show ? "true ":"false")+ "\n"});
  }

  public static void setOverDraw(boolean show){
    execShell(new String[]{"setprop debug.hwui.overdraw"+(show ? "show ":"false")+ "\n"});
  }

  public static void setGPULineBar(boolean show){
      execShell(new String[] { "setprop debug.hwui.profile "+(show ? "visual_bars ":"false")+ "\n"});
  }

  public static void execShell(String[] commands) {
    Process process = null;
    try {
      process = Runtime.getRuntime().exec("su");
      DataOutputStream os = new DataOutputStream(process.getOutputStream());
      for (String command : commands) {
        if (command == null) {
          continue;
        }
        os.write(command.getBytes());
        os.writeBytes("\n");
      }
      os.writeBytes("exit\n");
      os.flush();
      os.close();
      process.destroy();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}

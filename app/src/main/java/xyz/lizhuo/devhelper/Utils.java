package xyz.lizhuo.devhelper;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lizhuo on 16/3/27.
 */
public class Utils {

    private volatile static Process process = null;

    public Utils() {
        try {
            process = Runtime.getRuntime().exec("su\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIpAddress(Context context) {
        int ipAddress = 0;
        WifiManager wifiManager = (WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ipAddress = wifiInfo.getIpAddress();
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
    }

    public void startWifiAdb(boolean enable) {
        execShell(new String[]{"setprop service.adb.tcp.port " + (enable ? "5555" : "-1 \n"), "stop adbd\n", "start adbd\n"});
    }

    public void setDebugLayout(boolean show) {
        execShell(new String[]{"setprop debug.layout " + (show ? "true " : "false") + "\n"});
    }

    public void setOverDraw(boolean show) {
//    execShell(new String[]{"setprop debug.hwui.overdraw "+(show ? "show ":"false")+ "\n"});
        execShell(new String[]{"setprop debug.hwui.overdraw show \n"});
    }


    public void setGPULineBar(boolean show) {
        execShell(new String[]{"setprop debug.hwui.profile " + (show ? "visual_bars " : "false") + "\n"});
    }

    private void execShell(String[] commands) {
        try {
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            InputStream is = process.getInputStream();
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                os.write(command.getBytes());
                os.write("ls".getBytes());
                os.writeBytes("\n");
            }
            //os.writeBytes("exit\n");
            os.flush();
            os.close();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line =null;
            line = buf.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package info.marcin.plugins.serialport;

import android.annotation.SuppressLint;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import info.marcin.plugins.serialport.device.SerialDevice;
import java.util.Locale;

@SuppressLint("MissingPermission")
@CapacitorPlugin(
    name = "SerialPort",
    permissions = {
        @Permission(
            alias = "MANAGE_USB",
            strings = {
                // Manifest.android.permission.MANAGE_USB
                "android.permission.MANAGE_USB"
            }
        )
    }
)
public class SerialPortPlugin extends Plugin {

    private static final String TAG = "SerialPortPlugin";
    //     private SerialPort implementation = new SerialPort(this.getContext());
    //    private SerialPort implementation;// = new SerialPort(this.getActivity().getApplicationContext());
    private SerialPortManager implementation; // = new SerialPort(this.getActivity().getApplicationContext());

    @Override
    public void load() {
        implementation = new SerialPortManager(getContext());
    }

    @Override
    public void handleOnResume() {
        super.handleOnResume();
        implementation.handleOnResume();
    }

    @PluginMethod
    public void getPorts(PluginCall call) {
        JSObject ret = new JSObject();
        JSArray portsArray = new JSArray();
        try {
            for (SerialDevice deviceItem : this.implementation.getPorts()) {
                JSObject deviceInfo = new JSObject();
                deviceInfo.put("portId", deviceItem.getPortId());
                deviceInfo.put("driverName", deviceItem.getDriverName());
                deviceInfo.put("vendorId", deviceItem.getVendorId());
                deviceInfo.put("productId", deviceItem.getProductId());

                portsArray.put(deviceInfo);
            }

            ret.put("devices", portsArray);
            call.resolve(ret);
        } catch (Exception e) {
            Logger.error(TAG, e.getMessage(), e);
            call.reject(e.toString());
        }
    }

    @PermissionCallback
    private void manageUsbPermissionsCallback(PluginCall call) {
        if (getPermissionState("MANAGE_USB") == PermissionState.GRANTED) {
            //            loadCamera(call);
        } else {
            call.reject("Permission is required to open serial port");
        }
    }
}

package info.marcin.plugins.serialport;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import com.getcapacitor.Logger;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import info.marcin.plugins.serialport.device.CustomProber;
import info.marcin.plugins.serialport.device.SerialDevice;
import java.util.ArrayList;
import java.util.List;

public class SerialPortManager {

    private static final String TAG = "AndroidSerialPort";
    private String APPLICATION_ID;
    private String INTENT_ACTION_GRANT_USB;
    private Context context;

    private final ArrayList<SerialDevice> ports = new ArrayList<>();

    public SerialPortManager(Context context) {
        this.context = context; //.getApplicationContext();
        //        this.context = context.getApplicationContext();
        this.APPLICATION_ID = this.getActivity(this.context).getPackageName();
        this.INTENT_ACTION_GRANT_USB = this.APPLICATION_ID + ".GRANT_USB";
    }

    private Activity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

    public void handleOnResume() {
        try {
            Logger.info(TAG, "Serial port resume");
        } catch (Exception ex) {
            Logger.error(TAG, ex.getLocalizedMessage(), ex);
        }
    }

    public List<SerialDevice> getPorts() {
        return this.getPorts(new CustomProber());
    }

    public List<SerialDevice> getPorts(CustomProber customProber) {
        UsbManager usbManager = (UsbManager) this.getActivity(this.context).getSystemService(Context.USB_SERVICE);
        //        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
        UsbSerialProber usbProber = customProber.proberSize() == 0 ? UsbSerialProber.getDefaultProber() : customProber.getProber();
        ArrayList<CustomProber.VidPid> vidPidFilter = customProber.getFilter();
        this.ports.clear();
        int portId = 0;
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            UsbSerialDriver driver = usbProber.probeDevice(device);
            //            if(driver == null) {
            //                driver = usbCustomProber.probeDevice(device);
            //            }
            if (driver != null) {
                for (int port = 0; port < driver.getPorts().size(); port++) {
                    int vid = device.getDeviceId();
                    int pid = device.getProductId();
                    if (vidPidFilter.size() > 0) {
                        for (CustomProber.VidPid vidPid : vidPidFilter) {
                            if (vidPid.vendorId == vid && vidPid.productId == pid) {
                                this.ports.add(new SerialDevice(device, port, driver, portId));
                                portId++;
                                break;
                            }
                        }
                    } else {
                        this.ports.add(new SerialDevice(device, port, driver, portId));
                        portId++;
                    }
                }
            }
        }
        return this.ports;
    }
    //    public void requestPort

}

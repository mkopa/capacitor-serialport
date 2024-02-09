package info.marcin.plugins.serialport;

//import static android.app.PendingIntent.getActivity;

//import android.app.Activity;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
//import com.hoho.android.usbserial.driver.ProbeTable;
import com.getcapacitor.Logger;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SerialPort implements SerialInputOutputManager.Listener {

    private static final String TAG = "SerialPort";
    private Context context;

    static class ListItem {

        UsbDevice device;
        int port;
        UsbSerialDriver driver;

        ListItem(UsbDevice device, int port, UsbSerialDriver driver) {
            this.device = device;
            this.port = port;
            this.driver = driver;
        }
    }

    private final ArrayList<ListItem> listItems = new ArrayList<>();
    //    private ArrayAdapter<ListItem> listAdapter;

    private String APPLICATION_ID; // = "info.marcin.plugins.serialport";

    private enum UsbPermission {
        Unknown,
        Requested,
        Granted,
        Denied
    }

    private String INTENT_ACTION_GRANT_USB; // = SerialPort.APPLICATION_ID + ".GRANT_USB";
    private static final int WRITE_WAIT_MILLIS = 2000;
    private static final int READ_WAIT_MILLIS = 2000;

    //    private ProbeTable customTable = new ProbeTable();

    private int deviceId, portNum, baudRate = 115200;
    private boolean withIoManager = true;

    private final BroadcastReceiver broadcastReceiver;
    private final Handler mainLooper;

    private SerialInputOutputManager usbIoManager;
    private UsbSerialPort usbSerialPort;
    private UsbPermission usbPermission = UsbPermission.Unknown;
    private boolean connected = false;

    public SerialPort(Context context) {
        this.context = context; //.getApplicationContext();
        //        this.context = context.getApplicationContext();
        this.APPLICATION_ID = this.getActivity(this.context).getPackageName();
        String INTENT_ACTION_GRANT_USB = this.APPLICATION_ID + ".GRANT_USB";
        this.INTENT_ACTION_GRANT_USB = INTENT_ACTION_GRANT_USB;

        //        private static final String ACTION_USB_PERMISSION = BuildConfig.APPLICATION_ID + ".USB_PERMISSION";
        //        m_permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        //        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);

        // Broadcast Receiver
        //        else if (ACTION_USB_PERMISSION.equals(action)) {
        //            if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
        //                m_textViewDebug.setText("USB Permission Granted");
        //                try {
        //                    OnDevicePermissionGranted();
        //                } catch (Exception e) {
        //                    m_textViewDebug.setText(e.getMessage());
        //                }
        //            }
        //            else {
        //                m_textViewDebug.setText("USB Permission Denied");
        //            }
        //        }

        broadcastReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (INTENT_ACTION_GRANT_USB.equals(intent.getAction())) {
                        usbPermission =
                            intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                                ? UsbPermission.Granted
                                : UsbPermission.Denied;
                        connect();
                    }
                }
            };
        mainLooper = new Handler(Looper.getMainLooper());
    }

    public void handleOnResume() {
        try {
            Logger.info(TAG, "Serial port resume");
        } catch (Exception ex) {
            Logger.error(TAG, ex.getLocalizedMessage(), ex);
        }
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

    void refresh() {
        UsbManager usbManager = (UsbManager) this.getActivity(this.context).getSystemService(Context.USB_SERVICE);
        UsbSerialProber usbDefaultProber = UsbSerialProber.getDefaultProber();
        //                UsbSerialProber usbCustomProber = CustomProber.getCustomProber();
        listItems.clear();
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            UsbSerialDriver driver = usbDefaultProber.probeDevice(device);
            //            if(driver == null) {
            //                driver = usbCustomProber.probeDevice(device);
            //            }
            if (driver != null) {
                for (int port = 0; port < driver.getPorts().size(); port++) listItems.add(new ListItem(device, port, driver));
            } else {
                listItems.add(new ListItem(device, 0, null));
            }
        }
        //        listAdapter.notifyDataSetChanged();
    }

    /*
     * Serial
     */
    @Override
    public void onNewData(byte[] data) {
        mainLooper.post(
            () -> {
                receive(data);
            }
        );
    }

    @Override
    public void onRunError(Exception e) {
        mainLooper.post(
            () -> {
                Logger.error(TAG, "connection lost: " + e.getMessage(), e);
                disconnect();
            }
        );
    }

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }

    /*
     * Serial + UI
     */
    private void connect() {
        UsbDevice device = null;
        UsbManager usbManager = (UsbManager) this.getActivity(this.context).getSystemService(Context.USB_SERVICE);
        for (UsbDevice v : usbManager.getDeviceList().values()) if (v.getDeviceId() == deviceId) device = v;
        if (device == null) {
            Logger.error(TAG, "connection failed: device not found", new Exception("connection failed: device not found"));
            return;
        }
        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        //        if(driver == null) {
        //            driver = CustomProber.getCustomProber().probeDevice(device);
        //        }
        if (driver == null) {
            Logger.error(TAG, new Exception("connection failed: no driver for device"));
            return;
        }
        if (driver.getPorts().size() < portNum) {
            Logger.error(TAG, new Exception("connection failed: not enough ports at device"));
            return;
        }
        usbSerialPort = driver.getPorts().get(portNum);
        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
        if (usbConnection == null && usbPermission == UsbPermission.Unknown && !usbManager.hasPermission(driver.getDevice())) {
            usbPermission = UsbPermission.Requested;
            int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_MUTABLE : 0;
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(
                this.getActivity(this.context),
                0,
                new Intent(this.INTENT_ACTION_GRANT_USB),
                flags
            );
            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
            return;
        }
        if (usbConnection == null) {
            if (!usbManager.hasPermission(driver.getDevice())) Logger.error(
                TAG,
                new Exception("connection failed: permission denied")
            ); else Logger.error(TAG, new Exception("connection failed: open failed"));
            return;
        }

        try {
            usbSerialPort.open(usbConnection);
            try {
                usbSerialPort.setParameters(baudRate, 8, 1, UsbSerialPort.PARITY_NONE);
            } catch (UnsupportedOperationException e) {
                Logger.error(TAG, "unsupport setparameters: " + e.getMessage(), e);
            }
            if (withIoManager) {
                usbIoManager = new SerialInputOutputManager(usbSerialPort, this);
                usbIoManager.start();
            }
            Logger.info(TAG, "connected");
            connected = true;
            //            controlLines.start();
        } catch (Exception e) {
            Logger.error(TAG, "connection failed: " + e.getMessage(), e);
            disconnect();
        }
    }

    private void disconnect() {
        connected = false;
        //        controlLines.stop();
        if (usbIoManager != null) {
            usbIoManager.setListener(null);
            usbIoManager.stop();
        }
        usbIoManager = null;
        try {
            usbSerialPort.close();
        } catch (IOException ignored) {}
        usbSerialPort = null;
    }

    private void send(String str) {
        if (!connected) {
            //            Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            byte[] data = (str + '\n').getBytes();
            //            SpannableStringBuilder spn = new SpannableStringBuilder();
            //            spn.append("send " + data.length + " bytes\n");
            //            spn.append(HexDump.dumpHexString(data)).append("\n");
            //            spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorSendText)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //            receiveText.append(spn);
            usbSerialPort.write(data, WRITE_WAIT_MILLIS);
        } catch (Exception e) {
            onRunError(e);
        }
    }

    private void read() {
        if (!connected) {
            //            Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            byte[] buffer = new byte[8192];
            int len = usbSerialPort.read(buffer, READ_WAIT_MILLIS);
            receive(Arrays.copyOf(buffer, len));
        } catch (IOException e) {
            // when using read with timeout, USB bulkTransfer returns -1 on timeout _and_ errors
            // like connection loss, so there is typically no exception thrown here on error
            Logger.error(TAG, "connection lost: " + e.getMessage(), e);
            disconnect();
        }
    }

    private void receive(byte[] data) {
        //        SpannableStringBuilder spn = new SpannableStringBuilder();
        //        spn.append("receive " + data.length + " bytes\n");
        if (data.length > 0) {
            //            spn.append(HexDump.dumpHexString(data)).append("\n");
        }
        //        receiveText.append(spn);
    }
}

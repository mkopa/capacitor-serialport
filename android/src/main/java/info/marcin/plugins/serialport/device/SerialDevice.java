package info.marcin.plugins.serialport.device;

import android.hardware.usb.UsbDevice;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import java.util.Locale;

public class SerialDevice {

    private final int portId;
    private final String driverName;
    private final int vendorId;
    private final int productId;
    public final UsbDevice device;
    public final int portNumber;
    public final UsbSerialDriver driver;

    public SerialDevice(UsbDevice device, int port, UsbSerialDriver driver, int portId) {
        this.device = device;
        this.portNumber = port;
        this.driver = driver;
        this.portId = portId;

        this.driverName = driver.getClass().getSimpleName().replace("SerialDriver", "").toUpperCase(Locale.US);

        //        this.vendorId = String.format(Locale.US, "%04X", device.getVendorId());
        //        this.productId = String.format(Locale.US, "%04X", device.getProductId());

        this.vendorId = device.getVendorId();
        this.productId = device.getProductId();
    }

    public int getPortId() {
        return this.portId;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public int getVendorId() {
        return this.vendorId;
    }

    public int getProductId() {
        return this.productId;
    }
}

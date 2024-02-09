package info.marcin.plugins.serialport.device;

import com.hoho.android.usbserial.driver.UsbSerialDriver;

public class DeviceProduct {

    public final int vendorId;
    public final int productId;

    public final Class<? extends UsbSerialDriver> driver;

    //    public final DeviceDriverClass deviceDriverClass;

    public DeviceProduct(int vendorId, int productId, DeviceDriverClass driver) {
        this.vendorId = vendorId;
        this.productId = productId;
        this.driver = driver.getDriverClass();
    }
}

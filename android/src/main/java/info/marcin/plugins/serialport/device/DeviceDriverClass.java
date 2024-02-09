package info.marcin.plugins.serialport.device;

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.Ch34xSerialDriver;
import com.hoho.android.usbserial.driver.ChromeCcdSerialDriver;
import com.hoho.android.usbserial.driver.Cp21xxSerialDriver;
import com.hoho.android.usbserial.driver.FtdiSerialDriver;
import com.hoho.android.usbserial.driver.GsmModemSerialDriver;
import com.hoho.android.usbserial.driver.ProlificSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialDriver;

public enum DeviceDriverClass {
    FTDI(FtdiSerialDriver.class),
    CP21XX(Cp21xxSerialDriver.class),
    PROLIFIC(ProlificSerialDriver.class),
    CH34X(Ch34xSerialDriver.class),
    CDC_ACM(CdcAcmSerialDriver.class),
    CHROME_CCD(ChromeCcdSerialDriver.class),
    GSM_MODEM(GsmModemSerialDriver.class);

    Class<? extends UsbSerialDriver> driverClass;
    String driverClassName;
    String driverClassShortName;

    //
    DeviceDriverClass(Class<? extends UsbSerialDriver> name) {
        driverClass = name;
        driverClassName = name.getSimpleName();
        driverClassShortName = name.getSimpleName().replace("SerialDriver", "");
    }

    Class<? extends UsbSerialDriver> getDriverClass() {
        return driverClass;
    }

    String getDriverClassName() {
        return driverClassName;
    }

    String getDriverClassShortName() {
        return driverClassShortName;
    }

    @Override
    public String toString() {
        return getDriverClassName();
    }
}

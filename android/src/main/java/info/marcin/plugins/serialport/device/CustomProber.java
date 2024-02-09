package info.marcin.plugins.serialport.device;

import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import java.util.ArrayList;

public class CustomProber {

    public CustomProber() {
        this.customTable = new ProbeTable();
        this.vidPidFilter = new ArrayList<>();
        this.probeSize = 0;
    }

    public CustomProber(DeviceProduct products[]) {
        this.customTable = new ProbeTable();
        this.vidPidFilter = new ArrayList<>();
        this.addProduct(products);
    }

    public CustomProber(ArrayList<VidPid> filter) {
        this.customTable = new ProbeTable();
        this.vidPidFilter = filter;
    }

    public CustomProber(DeviceProduct products[], ArrayList<VidPid> filter) {
        this.customTable = new ProbeTable();
        this.vidPidFilter = new ArrayList<>();
        this.addProduct(products);
        this.vidPidFilter.clear();
        this.vidPidFilter = filter;
    }

    public static class VidPid {

        public int vendorId;
        public int productId;

        VidPid(int vendorId, int productId) {
            this.vendorId = vendorId;
            this.productId = productId;
        }
    }

    private ProbeTable customTable;
    private int probeSize;
    private ArrayList<VidPid> vidPidFilter; // = new ArrayList();

    public void addProduct(DeviceProduct product) {
        this.customTable.addProduct(product.vendorId, product.productId, product.driver);
        this.vidPidFilter.add(new VidPid(product.vendorId, product.productId));
        this.probeSize++;
    }

    public void addProduct(int vendorId, int productId, DeviceDriverClass driverClass) {
        this.addProduct(new DeviceProduct(vendorId, productId, driverClass));
    }

    public void addProduct(DeviceProduct products[]) {
        for (DeviceProduct product : products) {
            this.addProduct(product);
        }
    }

    public void addFilter(int vendorId, int productId) {
        this.vidPidFilter.add(new VidPid(vendorId, productId));
    }

    public UsbSerialProber getProber() {
        return new UsbSerialProber(this.customTable);
    }

    public ArrayList<VidPid> getFilter() {
        return this.vidPidFilter;
    }

    public int proberSize() {
        return this.probeSize;
    }

    public int filterSize() {
        return this.vidPidFilter.size();
    }
}

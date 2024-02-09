package info.marcin.plugins.serialport;

public class SerialPortConfig {

    public int deviceId;
    public int portNum;
    public int baudRate;

    SerialPortConfig() {
        this.baudRate = 115200;
    }

    SerialPortConfig(int deviceId, int portNum, int baudRate) {
        this.deviceId = deviceId;
        this.portNum = portNum;
        this.baudRate = baudRate;
    }
}

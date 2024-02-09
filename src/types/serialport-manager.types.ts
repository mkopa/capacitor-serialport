import type { SerialDeviceInfo } from './serial-device-info.types';
import type { UsbSerialPort } from './serialport-device.types';

export interface UsbSerialPortManager {
  getPorts(): Promise<SerialDeviceInfo[]>;
  requestPort(portId: number): Promise<UsbSerialPort>;
}

import type { SerialDeviceInfo } from './serial-device-info.types';

export interface UsbSerialPortDeviceDependencies {
  deviceInfo: SerialDeviceInfo;
}

export interface UsbSerialPortDevice {
  open(portId: string): Promise<void>;
  close(): Promise<void>;
  write(data: Uint8Array): Promise<void>;
  write(data: string): Promise<void>;
  getDeviceInfo(): Promise<SerialDeviceInfo>;
}

export type SerialPortDeviceEventsDefinition = {
  close: void;
  data: string;
};

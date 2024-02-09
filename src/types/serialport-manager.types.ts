import type { SerialDeviceInfo } from './serial-device-info.types';

export interface SerialPortManagerDef {
  getPorts(): Promise<SerialDeviceInfo[]>;
}

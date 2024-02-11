import type { SerialDeviceInfo } from './serial-device-info.types';
import type { UsbSerialPortDevice } from './serialport-device.types';

export interface UsbSerialPortManager {
  getPorts(): Promise<SerialDeviceInfo[]>;
  requestPort(portId: string): Promise<UsbSerialPortDevice>;
}

export type CloseReason = string;

export type Base64Data = string;

export type InternalPortEventsDefinition = {
  txData: Base64Data;
  rxData: Base64Data;
  closedByHost: CloseReason;
  closedByUser: string;
};

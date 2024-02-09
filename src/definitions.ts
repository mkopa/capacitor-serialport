import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

import type { SerialDeviceInfo } from './types/serial-device-info.types';
// import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export interface PermissionStatus {
  MANAGE_USB: PermissionState;
}
// export interface SerialPortOptions {
//
// }

export interface OpenPortOptions {
  portId: number;
  baudRate?: number;
}

export interface ClosePortOptions {
  portId: number;
}
// export interface OpenSerialPortResult {
//
//   // isOpen: () => boolean;
//   // setParameters: (parameters: SerialOptions) => Promise<void>;
// }

export interface GetPortsResult {
  devices: SerialDeviceInfo[];
}

export interface OnPortDataResult {
  portId: number;
  data: string;
}

export interface OnPortCloseResult {
  portId: number;
}

export type Data = DataView | string;

export interface ReadResult {
  /**
   * android, ios: string
   * web: DataView
   */
  value?: Data;
}

export interface SerialPortPlugin {
  getPorts(): Promise<GetPortsResult>;
  openPort(options: OpenPortOptions): Promise<void>;
  closePort(options: ClosePortOptions): Promise<void>;
  addListener(
    eventName: 'onData',
    listenerFunc: (result: OnPortDataResult) => void,
  ): PluginListenerHandle;
  addListener(
    eventName: 'onClose',
    listenerFunc: (result: OnPortCloseResult) => void,
  ): PluginListenerHandle;

  checkPermissions(): Promise<PermissionStatus>;
  requestPermissions(): Promise<PermissionStatus>;
}

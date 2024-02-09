import { WebPlugin } from '@capacitor/core';

import type {
  SerialPortPlugin,
  PermissionStatus,
  OpenPortOptions,
  ClosePortOptions,
  GetPortsResult,
} from './definitions';
import { getRandomInt } from './utils';

// export class
export class SerialPortWeb extends WebPlugin implements SerialPortPlugin {
  // private deviceMap = new Map<number, BluetoothDevice>();
  //
  // private onCharacteristicValueChangedCallback = this.onCharacteristicValueChanged.bind(this);
  //
  // private onCharacteristicValueChanged(event: Event): void {
  //   const characteristic = event.target as BluetoothRemoteGATTCharacteristic;
  //   const key = `notification|${characteristic.service?.device.id}|${characteristic.service?.uuid}|${characteristic.uuid}`;
  //   this.notifyListeners(key, {
  //     value: characteristic.value,
  //   });
  // }
  public async getPorts(): Promise<GetPortsResult> {
    const devices: GetPortsResult = {
      devices: [
        {
          portId: 0,
          driverName: 'FTDI',
          productId: getRandomInt(0x0001, 0xffff),
          vendorId: getRandomInt(0x0001, 0xffff),
        },
        {
          portId: 1,
          driverName: 'PROLIFIC',
          productId: getRandomInt(0x0001, 0xffff),
          vendorId: getRandomInt(0x0001, 0xffff),
        },
        {
          portId: 3,
          driverName: 'CP21XX',
          productId: getRandomInt(0x0001, 0xffff),
          vendorId: getRandomInt(0x0001, 0xffff),
        },
        {
          portId: 4,
          driverName: 'CH34X',
          productId: getRandomInt(0x0001, 0xffff),
          vendorId: getRandomInt(0x0001, 0xffff),
        },
      ],
    };
    return devices;
  }

  public async openPort(options: OpenPortOptions): Promise<void> {
    if (typeof navigator === 'undefined' || !(navigator as any)?.serial) {
      throw this.unavailable('Serial API not available in this browser.');
    }

    console.log('Open port: ', JSON.stringify(options));
  }

  public async closePort(options: ClosePortOptions): Promise<void> {
    if (typeof navigator === 'undefined' || !(navigator as any)?.serial) {
      throw this.unavailable('Serial API not available in this browser.');
    }

    console.log('Close port: ', JSON.stringify(options));
  }

  public async checkPermissions(): Promise<PermissionStatus> {
    if (typeof navigator === 'undefined' || !(navigator as any)?.serial) {
      throw this.unavailable('Serial API not available in this browser.');
    }

    return {
      MANAGE_USB: 'granted',
    };
  }

  public async requestPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }

  // private onDataCallback = this.onData.bind(this);
  //
  // private onData(event: Event): void {
  //   const deviceId = (event.target as BluetoothDevice).id;
  //   const key = `disconnected|${deviceId}`;
  //   this.notifyListeners(key, null);
  // }
  //
  // private getDeviceFromMap(deviceId: string): BluetoothDevice {
  //   const device = this.deviceMap.get(deviceId);
  //   if (device === undefined) {
  //     throw new Error('Device not found. Call "requestDevice", "requestLEScan" or "getDevices" first.');
  //   }
  //   return device;
  // }
}

import type { SerialPortPlugin } from './definitions';
import { UsbSerialPortDevice } from './serialport-device';
import type { SerialDeviceInfo } from './types/serial-device-info.types';
import type { UsbSerialPort } from './types/serialport-device.types';
import type { UsbSerialPortManager } from './types/serialport-manager.types';

export const getSerialPort = (
  serialPortPlugin: SerialPortPlugin,
): MockSerialManager => MockSerialManager.getInstance({ serialPortPlugin });

export interface MockSerialDependencies {
  serialPortPlugin: SerialPortPlugin;
}
export class MockSerialManager implements UsbSerialPortManager {
  private static instance: MockSerialManager;
  private constructor(private dependencies: MockSerialDependencies) {}

  public async getPorts(): Promise<SerialDeviceInfo[]> {
    const portList = await this.dependencies.serialPortPlugin.getPorts();

    return portList.devices;
  }

  public async requestPort(_portId: number): Promise<UsbSerialPort> {
    return new UsbSerialPortDevice();
  }

  public static getInstance(
    dependencies: MockSerialDependencies,
  ): MockSerialManager {
    if (this.instance) {
      return this.instance;
    }
    return new MockSerialManager(dependencies);
  }
}

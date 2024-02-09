import type { SerialPortPlugin } from './definitions';
import type { SerialDeviceInfo } from './types/serial-device-info.types';
import type { SerialPortManagerDef } from './types/serialport-manager.types';

export const getSerialPort = (
  serialPortPlugin: SerialPortPlugin,
): MockSerialManager => MockSerialManager.getInstance({ serialPortPlugin });

export interface MockSerialDependencies {
  serialPortPlugin: SerialPortPlugin;
}
export class MockSerialManager implements SerialPortManagerDef {
  private static instance: MockSerialManager;
  private constructor(private dependencies: MockSerialDependencies) {}

  public async getPorts(): Promise<SerialDeviceInfo[]> {
    const portList = await this.dependencies.serialPortPlugin.getPorts();

    return portList.devices;
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

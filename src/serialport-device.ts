import type { SerialDeviceInfo } from './types/serial-device-info.types';
import type {
  SerialPortDeviceEventsDefinition,
  UsbSerialPortDevice,
  UsbSerialPortDeviceDependencies,
} from './types/serialport-device.types';
import { SafeEventBus } from './util/safe-event-bus';

export class UsbSerialPort
  extends SafeEventBus<SerialPortDeviceEventsDefinition>
  implements UsbSerialPortDevice
{
  constructor(private readonly dependencies: UsbSerialPortDeviceDependencies) {
    super();
    this.dependencies = dependencies;
  }

  public async getDeviceInfo(): Promise<SerialDeviceInfo> {
    const { deviceInfo } = this.dependencies;

    return deviceInfo;
  }

  public async open(_portId: string): Promise<void> {
    // this.publish('')
  }
  public async close(): Promise<void> {
    // this.dispatchEvent('onClose', this.dependencies.deviceInfo.portId);
  }
  public async write(data: Uint8Array | string): Promise<void> {
    console.log(data);
  }
}

import type {
  SerialPortDeviceEventsDefinition,
  UsbSerialPort,
} from './types/serialport-device.types';
import { SafeEventBus } from './util/safe-event-bus';

export class UsbSerialPortDevice
  extends SafeEventBus<SerialPortDeviceEventsDefinition>
  implements UsbSerialPort
{
  public async open(): Promise<void> {
    // this.publish('')
  }
  public async close(): Promise<void> {
    this.publish('onClose');
  }

  public async write(data: string): Promise<void> {
    console.log(data);
  }
}

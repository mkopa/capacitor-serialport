import type {
  SerialPortDeviceDef,
  SerialPortDeviceEventsDefinition,
} from './types/serialport-device.types';
import { SafeEventBus } from './util/safe-event-bus';

export class SerialPortDevice
  extends SafeEventBus<SerialPortDeviceEventsDefinition>
  implements SerialPortDeviceDef
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

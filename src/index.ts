import { registerPlugin } from '@capacitor/core';

import type { SerialPortPlugin } from './definitions';
import { getSerialPort } from './serialport-manager';
import type { UsbSerialPortManager } from './types/serialport-manager.types';

const SerialPortManager: UsbSerialPortManager = getSerialPort(
  registerPlugin<SerialPortPlugin>('SerialPort', {
    web: () => import('./web').then(m => new m.SerialPortWeb()),
  }),
);

export { SerialPortManager };
// export

export * from './types/serialport-device.types';
export * from './types/serialport-manager.types';
export * from './types/serial-device-info.types';
export * from './types/safe-event-bus.types';
export * from './types/driver.types';

// export * from './definitions';

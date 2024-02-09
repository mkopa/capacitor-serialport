import { registerPlugin } from '@capacitor/core';

import type { SerialPortPlugin } from './definitions';
import { getSerialPort } from './serialport-manager';
import type { SerialPortManagerDef } from './types';

const SerialPortManager: SerialPortManagerDef = getSerialPort(
  registerPlugin<SerialPortPlugin>('SerialPort', {
    web: () => import('./web').then(m => new m.SerialPortWeb()),
  }),
);

export { SerialPortManager };
// export

export * from './types/index';

// export * from './definitions';

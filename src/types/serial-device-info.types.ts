import type { DriverName } from './driver.types';

export interface SerialDeviceInfo {
  portId: string;
  driverName: DriverName;
  vendorId: number;
  productId: number;
}

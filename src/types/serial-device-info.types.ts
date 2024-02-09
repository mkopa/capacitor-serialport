import type { DriverName } from './driver.types';

export interface SerialDeviceInfo {
  portId: number;
  driverName: DriverName;
  vendorId: number;
  productId: number;
}

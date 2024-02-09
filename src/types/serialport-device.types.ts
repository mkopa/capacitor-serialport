// export type

export interface UsbSerialPort {
  open(): Promise<void>;
  close(): Promise<void>;
  write(data: string): Promise<void>;
}

export type SerialPortDeviceEventsDefinition = {
  onClose: void;
  onData: string;
};

import type { PluginListenerHandle } from '@capacitor/core';

import type { SerialPortPlugin } from './definitions';
import { UsbSerialPort } from './serialport-device';
import type { EventBusObject, Unsubscribe } from './types/safe-event-bus.types';
import type { SerialDeviceInfo } from './types/serial-device-info.types';
import type { UsbSerialPortDevice } from './types/serialport-device.types';
import type {
  UsbSerialPortManager,
  InternalPortEventsDefinition,
} from './types/serialport-manager.types';
import { createSafeEventBus } from './util/safe-event-bus';

export const getSerialPort = (
  serialPortPlugin: SerialPortPlugin,
): MockSerialManager => MockSerialManager.getInstance({ serialPortPlugin });

export interface MockSerialDependencies {
  serialPortPlugin: SerialPortPlugin;
}

export class MockSerialManager implements UsbSerialPortManager {
  private static instance: MockSerialManager;

  private ports: Map<
    string,
    {
      serialPort: UsbSerialPort;
      busObject: EventBusObject<InternalPortEventsDefinition>;
    }
  >;
  private onDataListenerMap: Map<string, PluginListenerHandle>;
  private onClosedByUserListenerMap: Map<string, Unsubscribe>;
  private onTxDataListenerMap: Map<string, Unsubscribe>;

  private constructor(private dependencies: MockSerialDependencies) {
    this.ports = new Map();
    this.onDataListenerMap = new Map();
    this.onClosedByUserListenerMap = new Map();
    this.onTxDataListenerMap = new Map();
  }

  private removePortListenerHandlers(portId: string) {
    if (this.onDataListenerMap.has(portId)) {
      this.onDataListenerMap.get(portId)?.remove();
    }
    if (this.onClosedByUserListenerMap.has(portId)) {
      const remove = this.onClosedByUserListenerMap.get(portId);
      if (remove) {
        remove();
      }
    }
    if (this.onTxDataListenerMap.has(portId)) {
      const remove = this.onTxDataListenerMap.get(portId);
      if (remove) {
        remove();
      }
    }
    this.dependencies.serialPortPlugin.addListener('onClose', result => {
      const onClosePortId = result.portId;
      if (!this.ports.has(onClosePortId)) {
        this.removePortListenerHandlers(onClosePortId);
      }
      console.log(
        '@mkopa/capacitor-serialport PORT CLOSED BY HOST: ',
        result.portId,
        result.reason,
      );
      this.ports
        .get(onClosePortId)
        ?.busObject.dispatchEvent('closedByHost', result.reason);
    });
  }

  public async getPorts(): Promise<SerialDeviceInfo[]> {
    const portList = await this.dependencies.serialPortPlugin.getPorts();

    return portList.devices;
  }

  public async requestPort(portId: string): Promise<UsbSerialPortDevice> {
    const { serialPortPlugin } = this.dependencies;
    const { devices } = await serialPortPlugin.getPorts();
    const deviceInfo = devices.find(device => device.portId === portId);
    if (!deviceInfo) {
      throw new Error('Invalid Port ID!');
    }
    const serialPort = new UsbSerialPort({ deviceInfo });
    const { eventBus, addEventListener, dispatchEvent } =
      createSafeEventBus<InternalPortEventsDefinition>();

    // if (this.ports.has(portId)) {
    //   return this.ports.get(portId).serialPort;
    // }
    this.ports.set(portId, {
      serialPort,
      busObject: { eventBus, addEventListener, dispatchEvent },
    });
    const unsubscribeTxDataEvent = addEventListener('txData', data => {
      console.log('@mkopa/capacitor-serialport TX DATA: ', data);
    });

    const onDataListener = this.dependencies.serialPortPlugin.addListener(
      'onData',
      result => {
        const onDataPortId = result.portId;
        if (!this.ports.has(onDataPortId)) {
          this.removePortListenerHandlers(onDataPortId);
        }
        console.log(
          '@mkopa/capacitor-serialport RX DATA: ',
          result.portId,
          result.data,
        );
        this.ports
          .get(onDataPortId)
          ?.busObject.dispatchEvent('rxData', result.data);
      },
    );

    const unsubscribeClosedByUserEvent = addEventListener(
      'closedByUser',
      closedPortId => {
        // eventBus.removeEventListener('closedByUser');
        this.ports.delete(closedPortId);
        this.removePortListenerHandlers(closedPortId);
      },
    );

    this.onClosedByUserListenerMap.set(portId, unsubscribeClosedByUserEvent);
    this.onDataListenerMap.set(portId, onDataListener);
    this.onTxDataListenerMap.set(portId, unsubscribeTxDataEvent);

    return serialPort;
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

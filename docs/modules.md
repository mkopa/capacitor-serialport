[@mkopa/capacitor-serialport](README.md) / Exports

# @mkopa/capacitor-serialport

## Table of contents

### Interfaces

- [SerialDeviceInfo](interfaces/SerialDeviceInfo.md)
- [SerialPortDeviceDef](interfaces/SerialPortDeviceDef.md)
- [SerialPortManagerDef](interfaces/SerialPortManagerDef.md)

### Type Aliases

- [AndroidDriverName](modules.md#androiddrivername)
- [DriverName](modules.md#drivername)
- [SerialPortDeviceEventsDefinition](modules.md#serialportdeviceeventsdefinition)
- [WebDriverName](modules.md#webdrivername)

## Type Aliases

### AndroidDriverName

Ƭ **AndroidDriverName**: ``"WEB"`` \| ``"FTDI"`` \| ``"CP21XX"`` \| ``"PROLIFIC"`` \| ``"CH34X"`` \| ``"CDC_ACM"`` \| ``"GDM_MODEM"``

#### Defined in

driver.types.ts:3

___

### DriverName

Ƭ **DriverName**: [`WebDriverName`](modules.md#webdrivername) \| [`AndroidDriverName`](modules.md#androiddrivername)

#### Defined in

driver.types.ts:12

___

### SerialPortDeviceEventsDefinition

Ƭ **SerialPortDeviceEventsDefinition**: `Object`

#### Type declaration

| Name | Type |
| :------ | :------ |
| `onClose` | `void` |
| `onData` | `string` |

#### Defined in

serialport-device.types.ts:9

___

### WebDriverName

Ƭ **WebDriverName**: ``"WEB"``

#### Defined in

driver.types.ts:1

@mkopa/capacitor-serialport / [Exports](modules.md)

# @mkopa/capacitor-serialport

Capacitor plugin for Serial Port

## Install

```bash
npm install @mkopa/capacitor-serialport
npx cap sync
```

## API

<docgen-index>

* [`getPorts()`](#getports)
* [`openPort(...)`](#openport)
* [`closePort(...)`](#closeport)
* [`addListener('onData', ...)`](#addlistenerondata-)
* [`addListener('onClose', ...)`](#addlisteneronclose-)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getPorts()

```typescript
getPorts() => Promise<GetPortsResult>
```

**Returns:** <code>Promise&lt;<a href="#getportsresult">GetPortsResult</a>&gt;</code>

--------------------

### openPort(...)

```typescript
openPort(options: OpenPortOptions) => Promise<void>
```

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#openportoptions">OpenPortOptions</a></code> |

--------------------

### closePort(...)

```typescript
closePort(options: ClosePortOptions) => Promise<void>
```

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#closeportoptions">ClosePortOptions</a></code> |

--------------------

### addListener('onData', ...)

```typescript
addListener(eventName: 'onData', listenerFunc: (result: OnPortDataResult) => void) => PluginListenerHandle
```

| Param              | Type                                                                               |
| ------------------ | ---------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onData'</code>                                                              |
| **`listenerFunc`** | <code>(result: <a href="#onportdataresult">OnPortDataResult</a>) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------

### addListener('onClose', ...)

```typescript
addListener(eventName: 'onClose', listenerFunc: (result: OnPortCloseResult) => void) => PluginListenerHandle
```

| Param              | Type                                                                                 |
| ------------------ | ------------------------------------------------------------------------------------ |
| **`eventName`**    | <code>'onClose'</code>                                                               |
| **`listenerFunc`** | <code>(result: <a href="#onportcloseresult">OnPortCloseResult</a>) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------

### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------

### Interfaces

#### GetPortsResult

| Prop          | Type                            |
| ------------- | ------------------------------- |
| **`devices`** | <code>SerialDeviceInfo[]</code> |

#### SerialDeviceInfo

| Prop             | Type                                              |
| ---------------- | ------------------------------------------------- |
| **`portId`**     | <code>number</code>                               |
| **`driverName`** | <code><a href="#drivername">DriverName</a></code> |
| **`vendorId`**   | <code>number</code>                               |
| **`productId`**  | <code>number</code>                               |

#### OpenPortOptions

| Prop           | Type                |
| -------------- | ------------------- |
| **`portId`**   | <code>number</code> |
| **`baudRate`** | <code>number</code> |

#### ClosePortOptions

| Prop         | Type                |
| ------------ | ------------------- |
| **`portId`** | <code>number</code> |

#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

#### OnPortDataResult

| Prop         | Type                |
| ------------ | ------------------- |
| **`portId`** | <code>number</code> |
| **`data`**   | <code>string</code> |

#### OnPortCloseResult

| Prop         | Type                |
| ------------ | ------------------- |
| **`portId`** | <code>number</code> |

#### PermissionStatus

| Prop             | Type                                                        |
| ---------------- | ----------------------------------------------------------- |
| **`MANAGE_USB`** | <code><a href="#permissionstate">PermissionState</a></code> |

### Type Aliases

#### DriverName

<code><a href="#webdrivername">WebDriverName</a> | <a href="#androiddrivername">AndroidDriverName</a></code>

#### WebDriverName

<code>'WEB'</code>

#### AndroidDriverName

<code>'WEB' | 'FTDI' | 'CP21XX' | 'PROLIFIC' | 'CH34X' | 'CDC_ACM' | 'GDM_MODEM'</code>

#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>

</docgen-api>

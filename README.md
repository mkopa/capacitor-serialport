# 📦 @mkopa/capacitor-serialport

![workflow](https://github.com/mkopa/capacitor-serialport/actions/workflows/test.yml/badge.svg)

### ❌ <span style="color:red;"> This package is still under development and NOT ready for production use.</span>

[Capacitor](https://capacitorjs.com) plugin for USB Serial Port

This is a driver library for communication with Arduinos and other USB serial hardware.

---

## ✨ Supported Platforms

- Android [USB Host Mode (OTG)](http://developer.android.com/guide/topics/connectivity/usb/host.html)
- Browsers - [Supported list](https://caniuse.com/?search=navigator.serial)
- Linux, macOS and Windows [Electron cross-platform desktop applications](https://capacitor-community.github.io/electron)

## ✨ Compatible Devices

&nbsp;✅ **USB to serial converter chips:**

- FTDI FT232R, FT232H, FT2232H, FT4232H, FT230X, FT231X, FT234XD
- Prolific PL2303
- Silabs CP2102, CP210*
- Qinheng CH340, CH341A, CH9102

&nbsp;✅ **Devices implementing the CDC/ACM protocol like:**

- STM32
- Arduino using ATmega32U4
- Digispark using V-USB software USB
- BBC micro:bit using ARM mbed DAPLink firmware...

&nbsp;✅ **Some device specific drivers:**

- GsmModem devices, e.g. for Unisoc based Fibocom GSM modems
- Chrome OS CCD (Closed Case Debugging)

## 💿 Install

```bash
npm install @mkopa/capacitor-serialport
npx cap sync
```

## 📜 API

<docgen-index>

* [`getPorts()`](#getports)
* [`requestPort(...)`](#requestport)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getPorts()

```typescript
getPorts() => Promise<SerialDeviceInfo[]>
```

**Returns:** <code>Promise&lt;SerialDeviceInfo[]&gt;</code>

--------------------


### requestPort(...)

```typescript
requestPort(portId: number) => Promise<UsbSerialPort>
```

| Param        | Type                |
| ------------ | ------------------- |
| **`portId`** | <code>number</code> |

**Returns:** <code>Promise&lt;<a href="#usbserialport">UsbSerialPort</a>&gt;</code>

--------------------


### Interfaces


#### SerialDeviceInfo

| Prop             | Type                                              |
| ---------------- | ------------------------------------------------- |
| **`portId`**     | <code>number</code>                               |
| **`driverName`** | <code><a href="#drivername">DriverName</a></code> |
| **`vendorId`**   | <code>number</code>                               |
| **`productId`**  | <code>number</code>                               |


#### UsbSerialPort

| Method    | Signature                                |
| --------- | ---------------------------------------- |
| **open**  | () =&gt; Promise&lt;void&gt;             |
| **close** | () =&gt; Promise&lt;void&gt;             |
| **write** | (data: string) =&gt; Promise&lt;void&gt; |


### Type Aliases


#### DriverName

<code><a href="#webdrivername">WebDriverName</a> | <a href="#androiddrivername">AndroidDriverName</a></code>


#### WebDriverName

<code>'WEB'</code>


#### AndroidDriverName

<code>'FTDI' | 'CP21XX' | 'PROLIFIC' | 'CH34X' | 'CDC_ACM' | 'GSM_MODEM'</code>

</docgen-api>

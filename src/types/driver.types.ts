export type WebDriverName = 'WEB';

export type AndroidDriverName =
  | 'FTDI'
  | 'CP21XX'
  | 'PROLIFIC'
  | 'CH34X'
  | 'CDC_ACM'
  | 'GSM_MODEM';

export type DriverName = WebDriverName | AndroidDriverName;

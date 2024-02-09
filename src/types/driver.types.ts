export type WebDriverName = 'WEB';

export type AndroidDriverName =
  | 'WEB'
  | 'FTDI'
  | 'CP21XX'
  | 'PROLIFIC'
  | 'CH34X'
  | 'CDC_ACM'
  | 'GDM_MODEM';

export type DriverName = WebDriverName | AndroidDriverName;

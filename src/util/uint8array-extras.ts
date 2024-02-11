// eslint-disable-next-line
export function isUint8Array(value): boolean {
  if (!value) {
    return false;
  }

  if (value.constructor === Uint8Array) {
    return true;
  }

  return Object.prototype.toString.call(value) === '[object Uint8Array]';
}

// eslint-disable-next-line
export function assertUint8Array(value): void {
  if (!isUint8Array(value)) {
    throw new TypeError(`Expected \`Uint8Array\`, got \`${typeof value}\``);
  }
}

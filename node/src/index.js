export function encodeInterop(operation, payload) {
  return JSON.stringify({schema:'chimera.crypto.interop',version:1,operation,payload});
}
export function decodeInterop(text) {
  const value=JSON.parse(text);
  if(value.schema!=='chimera.crypto.interop'||value.version!==1) throw new Error('unsupported interoperability envelope');
  return value;
}

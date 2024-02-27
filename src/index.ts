import { registerPlugin } from '@capacitor/core';

import type { CapCameraPermissionPlugin } from './definitions';

const CapCameraPermission = registerPlugin<CapCameraPermissionPlugin>('CapCameraPermission', {
  web: () => import('./web').then(m => new m.CapCameraPermissionWeb()),
});

export * from './definitions';
export { CapCameraPermission };

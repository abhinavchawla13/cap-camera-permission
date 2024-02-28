import { WebPlugin } from '@capacitor/core';

import type {
  CameraPluginPermissions,
  CapCameraPermissionPlugin,
} from './definitions';

export class CapCameraPermissionWeb
  extends WebPlugin
  implements CapCameraPermissionPlugin
{
  checkPermissions(): Promise<{ result: PermissionState }> {
    throw new Error('Method not implemented.');
  }
  requestPermissions(
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    _permissions?: CameraPluginPermissions | undefined,
  ): Promise<{ result: PermissionState }> {
    throw new Error('Method not implemented.');
  }
}

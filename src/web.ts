import { WebPlugin } from '@capacitor/core';

import type {
  CameraPluginPermissions,
  CapCameraPermissionPlugin,
} from './definitions';

export class CapCameraPermissionWeb
  extends WebPlugin
  implements CapCameraPermissionPlugin
{
  async checkPermissions(): Promise<{ camera: PermissionState }> {
    if (typeof navigator === 'undefined' || !navigator.permissions) {
      throw this.unavailable('Permissions API not available in this browser');
    }

    try {
      // https://developer.mozilla.org/en-US/docs/Web/API/Permissions/query
      // the specific permissions that are supported varies among browsers that implement the
      // permissions API, so we need a try/catch in case 'camera' is invalid
      const permission = await window.navigator.permissions.query({
        name: 'camera',
      });
      return {
        camera: permission.state,
      };
    } catch {
      throw this.unavailable(
        'Camera permissions are not available in this browser',
      );
    }
  }

  requestPermissions(
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    _permissions?: CameraPluginPermissions | undefined,
  ): Promise<{ camera: PermissionState }> {
    return this.checkPermissions();
  }
}

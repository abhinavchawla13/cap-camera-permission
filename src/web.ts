import { WebPlugin } from '@capacitor/core';

import type { CapCameraPermissionPlugin } from './definitions';

export class CapCameraPermissionWeb extends WebPlugin implements CapCameraPermissionPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

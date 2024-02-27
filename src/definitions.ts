export interface CapCameraPermissionPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

export interface CameraPluginPermissions {
  permissions: ['camera'];
}
export interface CapCameraPermissionPlugin {
  /**
   * Check camera and photo album permissions
   *
   * @since 1.0.0
   */
  checkPermissions(): Promise<{ result: PermissionState }>;

  /**
   * Request camera and photo album permissions
   *
   * @since 1.0.0
   */
  requestPermissions(
    permissions?: CameraPluginPermissions,
  ): Promise<{ result: PermissionState }>;
}

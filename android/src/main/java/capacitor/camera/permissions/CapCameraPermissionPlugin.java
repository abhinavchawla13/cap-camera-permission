package capacitor.camera.permissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import java.util.List;
import java.util.Map;
import org.json.JSONException;

@SuppressLint("InlinedApi")
@CapacitorPlugin(
    name = "CapCameraPermission",
    permissions = { @Permission(strings = { Manifest.permission.CAMERA }, alias = CapCameraPermissionPlugin.CAMERA) }
)
public class CapCameraPermissionPlugin extends Plugin {

    static final String CAMERA = "camera";
    private static final String PERMISSION_DENIED_ERROR_CAMERA = "User denied access to camera";
    private static final String NO_CAMERA_ERROR = "No camera";


    // https://capacitorjs.com/docs/plugins/android#permissions
    // based on this, checkPermissions and requestPermission should be fully functional by default
    // once we had the permissions on the top

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        // If the camera permission is defined in the manifest, then we have to prompt the user
        // or else we will get a security exception when trying to present the camera. If, however,
        // it is not defined in the manifest then we don't need to prompt and it will just work.
        Logger.info("Trying to request for permissions");
        if (isPermissionDeclared(CAMERA)) {
            // just request normally
            Logger.info("Trying to request for permissions inside");
            super.requestPermissions(call);
        }
    }

    private boolean checkCameraPermissions(PluginCall call) {
        // if the manifest does not contain the camera permissions key, we don't need to ask the user
        boolean needCameraPerms = isPermissionDeclared(CAMERA);
        boolean hasCameraPerms = !needCameraPerms || getPermissionState(CAMERA) == PermissionState.GRANTED;

        // If we don't need to save to the gallery, we can just ask for camera permissions
        if (!hasCameraPerms) {
            requestPermissionForAlias(CAMERA, call, "cameraPermissionsCallback");
            return false;
        }
        return true;
    }

    /**
     * Completes the plugin call after a camera permission request
     *
     * @param call the plugin call
     */
    @PermissionCallback
    private void cameraPermissionsCallback(PluginCall call) {
            if (getPermissionState(CAMERA) != PermissionState.GRANTED) {
                Logger.debug(getLogTag(), "User denied camera permission: " + getPermissionState(CAMERA).toString());
                call.reject(PERMISSION_DENIED_ERROR_CAMERA);
                return;
            }
        showCamera(call);
    }

    private void showCamera(final PluginCall call) {
        if (!getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            call.reject(NO_CAMERA_ERROR);
        }
    }

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        // If the camera permission is defined in the manifest, then we have to prompt the user
        // or else we will get a security exception when trying to present the camera. If, however,
        // it is not defined in the manifest then we don't need to prompt and it will just work.
        Logger.info("Trying to request for permissions inside check");
        super.checkPermissions(call);
    }

    @Override
    public Map<String, PermissionState> getPermissionStates() {
        Map<String, PermissionState> permissionStates = super.getPermissionStates();

        Logger.info("Trying togetPermissionStates", permissionStates.toString());
        // If Camera is not in the manifest and therefore not required, say the permission is granted
        if (!isPermissionDeclared(CAMERA)) {
            permissionStates.put(CAMERA, PermissionState.GRANTED);
        }

        return permissionStates;
    }
}

package capacitor.camera.permissions;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapCameraPermission")
public class CapCameraPermissionPlugin extends Plugin {

    @PluginMethod
    public void checkPermissions(PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }
}

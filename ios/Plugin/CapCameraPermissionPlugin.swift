import Foundation
import Capacitor
import AVFoundation


/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */

internal enum CameraPermissionType: String, CaseIterable {
    case camera
}

@objc(CapCameraPermissionPlugin)
public class CapCameraPermissionPlugin: CAPPlugin {
    
    public let pluginMethods: [CAPPluginMethod] = [
            CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
            CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        ]
    
    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        for permission in CameraPermissionType.allCases {
            let state: String
            switch permission {
            case .camera:
                let status = AVCaptureDevice.authorizationStatus(for: .video)
                
                switch status {
                            case .authorized:
                                state = "granted"
                            case .denied:
                                state = "denied"
                            case .restricted:
                                state = "denied"
                            case .notDetermined:
                                fallthrough
                            @unknown default:
                                state = "prompt"
                }
            }
            call.resolve(["result": state])
        }
    }
    
    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        // get the list of desired types, if passed
        let typeList = call.getArray("permissions", String.self)?.compactMap({ (type) -> CameraPermissionType? in
            return CameraPermissionType(rawValue: type)
        }) ?? []
        // otherwise check everything
        let permissions: [CameraPermissionType] = (typeList.count > 0) ? typeList : CameraPermissionType.allCases
        // request the permissions
        let group = DispatchGroup()
        for permission in permissions {
            switch permission {
            case .camera:
                group.enter()
                AVCaptureDevice.requestAccess(for: .video) { _ in
                    group.leave()
                }
            }
            group.notify(queue: DispatchQueue.main) { [weak self] in
                self?.checkPermissions(call)
            }
        }
    }

}

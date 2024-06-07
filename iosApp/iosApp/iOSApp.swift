import SwiftUI
import Firebase
import ComposeApp

@main
struct iOSApp: App {

    init() {
        HelperKt.doInitKoin()
        FirebaseApp.configure()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}

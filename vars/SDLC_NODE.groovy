def call(nodeName, librariesPaths, body) {
    def libs = [:]
    
	stage("Initialize libraries") {
		libs = libraryLoader.loadLibraries(librariesPaths)
	}

	node(nodeName) {
		body(libs)
	}
}

def call(nodeName, libPath, body) {
	libs = libraryLoader.loadLibraries([libPath])

	node(nodeName) {
		body(libs)
	}
}

return this

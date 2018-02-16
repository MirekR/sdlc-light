def createFilePath(def path) {
    echo path
    if (env['NODE_NAME'].equals("master")) {
        File localPath = new File(path)
        return new hudson.FilePath(localPath);
    } else {
        return new hudson.FilePath(Jenkins.getInstance().getComputer(env['NODE_NAME']).getChannel(), path);
    }
}

def loadAllFiles(rootPath, path, libs) {
    for (subPath in rootPath.list()) {
        fileName = subPath.getBaseName()
        if (!subPath.isDirectory()) {
           echo "loading library ${fileName}"
 	       

 	       // loadedLib = load "${path}/${fileName}.groovy"
          
           //   libs[fileName] = loadedLib
           this.class.classLoader.rootLoader.addURL(new URL("file:///${path}/${fileName}.groovy"))

	    }
    }

    return libs
}

def loadLibraries(librariesPaths) {
	def libs = [:]
	node('master') {
		for (path in librariesPaths) {
			libs = loadAllFiles(createFilePath(path), path, libs)
		}
	}

	return libs
}
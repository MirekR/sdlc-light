libs = [:]

def call(nodeName, librariesPaths, body) {
	stage("Initialize libraries") {
		node('master') {
			for (path in librariesPaths) {
				libs = loadAllFiles(createFilePath(path))
			}
		}
	}

	node(nodeName) {
		body()
	}
}

def getLibraries() {
	return libs
}

def loadAllFiles(rootPath, libs) {
    for (subPath in rootPath.list()) {
        fileName = subPath.getBaseName()
        if (!subPath.isDirectory()) {
           echo "loading library ${fileName}"
 	       
 	       loadedLib = load "${libPath}/${fileName}.groovy"
          
           libs[fileName] = loadedLib
	    }
    }

    return libs
}

// Helps if slave servers are in picture
def createFilePath(def path) {
    echo path
    if (env['NODE_NAME'].equals("master")) {
        File localPath = new File(path)
        return new hudson.FilePath(localPath);
    } else {
        return new hudson.FilePath(Jenkins.getInstance().getComputer(env['NODE_NAME']).getChannel(), path);
    }
}
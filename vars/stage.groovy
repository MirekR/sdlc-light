def call(stageName, body) {
	echo "Starting stage ${stageName}"
	
	body()

	echo "Starting stage ${stageName}"
}

return this
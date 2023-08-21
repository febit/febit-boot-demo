
.PHONY: test
test:
	./gradlew test

.PHONY: check
check:
	./gradlew check

.PHONY: clean
clean:
	./gradlew clean

.PHONY: licenseFormat
licenseFormat:
	./gradlew licenseFormat

.PHONY: build
build:
	./gradlew build

.PHONY: build-fast
build-fast:
	./gradlew build -x check -x test

.PHONY: build-image
build-image:
	docker build \
 		-t febit/febit-boot-demo \
		-f Dockerfile \
		--build-arg JAR_FILE=doggy/build/libs/febit-boot-demo-doggy.jar \
		--load \
		.

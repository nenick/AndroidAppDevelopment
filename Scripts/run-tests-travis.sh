#!/bin/bash
GRADLE_OPTS="-XX:MaxPermSize=512m"

./gradlew test \
          jacocoReport \
          coveralls \
          build \
          App:connectedAndroidTest \
          -PtravisCi -PdisablePreDex

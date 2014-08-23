#!/bin/bash
set -e
set -v
./gradlew dist
scp desktop/build/libs/desktop-1.0.jar alynn.co.uk:public_html/space.jar


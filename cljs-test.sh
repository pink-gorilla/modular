#!/bin/bash

clojure -X:webly:npm-install
clojure -X:webly:build-ci
npm test

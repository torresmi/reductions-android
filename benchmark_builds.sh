#!/bin/bash

gradle-profiler --benchmark --project-dir . --scenario-file build-scenarios.txt --warmups 2 --iterations 3 --measure-build-op --measure-gc

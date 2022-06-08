#!/bin/bash 
find . -name build -exec rm -rf {} \;
find . -name '.DS_Store' -type f -delete
find . -name ".DS_Store" -type d -exec rm -r "{}" \;
find . -name ".hprof" -type d -exec rm -r "{}" \;
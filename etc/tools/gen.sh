#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "$0")" || exit; pwd)"
ROOT_DIR="$SCRIPT_DIR/../.."

export BASE_PKG="org.febit.boot.demo.doggy"
export MODULE_DIR="$ROOT_DIR/doggy"

source "$SCRIPT_DIR/lib-gen.sh"

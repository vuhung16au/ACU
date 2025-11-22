#!/bin/bash
# Helper functions for video recording

show_title() {
    clear
    echo "╔════════════════════════════════════════╗"
    printf "║  %-36s  ║\n" "$1"
    echo "╚════════════════════════════════════════╝"
    echo
}

show_section() {
    echo
    echo "═══ $1 ═══"
    echo
}

run_with_pause() {
    local cmd="$1"
    local pause="${2:-2}"
    echo "\$ $cmd"
    sleep 1
    eval "$cmd" || true
    sleep "$pause"
}

wait_for_completion() {
    echo "Waiting for operation to complete..."
    sleep 2
}


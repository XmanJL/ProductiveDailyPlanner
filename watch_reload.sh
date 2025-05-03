#!/bin/bash

SRC_DIR="./src/main/java"
MAIN_CLASS="com.example.Dashboard"

echo "👀 Watching for changes using fswatch..."

run_app() {
    echo "🔧 Rebuilding and launching app..."
    pkill -f "$MAIN_CLASS" 2>/dev/null
    mvn clean javafx:run -Dexec.mainClass="$MAIN_CLASS"
}

# Initial launch
run_app &

# Watch for changes in .java files and trigger rebuild
fswatch -o "$SRC_DIR" | while read change; do
    echo "📦 Source changed. Reloading..."
    run_app &
done


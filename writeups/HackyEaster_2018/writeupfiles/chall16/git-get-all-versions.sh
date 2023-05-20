#!/bin/sh

# based on script provided by Dmitry Shevkoplyas at http://stackoverflow.com/questions/12850030/git-getting-all-previous-version-of-a-specific-file-folder

set -e

if ! git rev-parse --show-toplevel >/dev/null 2>&1 ; then
    echo "Error: you must run this from within a git working directory" >&2
    exit 1
fi

if [ "$#" -lt 1 ] || [ "$#" -gt 2 ]; then
    echo "Usage: $0 <relative path to file> [<output directory>]" >&2
    exit 2
fi

FILE_PATH="$1"

EXPORT_TO=./all_versions_exported
if [ -n "$2" ]; then
    EXPORT_TO="$2"
fi

FILE_NAME="$(basename "$FILE_PATH")"

if [ ! -d "$EXPORT_TO" ]; then
    echo "Creating directory '$EXPORT_TO'"
    mkdir -p "$EXPORT_TO"
fi

echo "Writing files to '$EXPORT_TO'"
git log --diff-filter=d --date-order --reverse --format="%ad %H" --date=iso-strict "$FILE_PATH" | grep -v '^commit' | \
    while read LINE; do \
        COMMIT_DATE=`echo $LINE | cut -d ' ' -f 1`; \
        COMMIT_SHA=`echo $LINE | cut -d ' ' -f 2`; \
        printf '.' ; \
        git cat-file -p "$COMMIT_SHA:$FILE_PATH" > "$EXPORT_TO/$COMMIT_DATE.$COMMIT_SHA.$FILE_NAME" ; \
    done
echo

exit 0

#!/bin/bash
FILENAME="DynamicShop.tar.gz"
REMOTE="work@sebbaindustries.com"
BUILD_DIR="/tmp"

CURRENT=$(pwd)
BASENAME=$(basename "$CURRENT")

clean_flag='false'
BUILD_FLAGS=""

while getopts 'c' flag; do
  case "${flag}" in
    c) clean_flag='true' ;;
  esac
done

if [ "$clean_flag" = "true" ]; then
    BUILD_FLAGS="-c"
fi

cd ..

tar \
--exclude="${BASENAME}/.git" \
--exclude="${BASENAME}/.gitignore" \
--exclude="${BASENAME}/.github" \
--exclude="${BASENAME}/target" \
--exclude="${BASENAME}/.idea" \
--exclude="${BASENAME}/*.md" \
--exclude="${BASENAME}/LICENSE" \
--exclude="${BASENAME}/*.iml" \
--exclude="${BASENAME}/remote.sh" \
-czvf $FILENAME "${BASENAME}"/

rsync -avP $FILENAME "${REMOTE}":"${BUILD_DIR}"

ssh ${REMOTE} "
cd /tmp/ &&
tar -xvf $FILENAME &&
cd ${BASENAME} &&
./build.sh ${BUILD_FLAGS}
"

echo "Done!"
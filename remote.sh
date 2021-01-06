#!/bin/bash
FILENAME="DynamicShop.tar.gz"
REMOTE="work@op65n.tech"
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
--exclude="${BASENAME}/src/test" \
--exclude="${BASENAME}/database" \
--exclude="${BASENAME}/.scripts" \
--exclude="${BASENAME}/*.log" \
--exclude="${BASENAME}/*.bat" \
--exclude="${BASENAME}/build" \
--exclude="${BASENAME}/.gradle" \
--exclude="${BASENAME}/gradle" \
-czvf $FILENAME "${BASENAME}"/

rsync -avP $FILENAME "${REMOTE}":"${BUILD_DIR}"

ssh ${REMOTE} "
cd /tmp/ &&
rm -rf ${BASENAME} &&
tar -xvf $FILENAME &&
cd ${BASENAME} &&
./build.sh ${BUILD_FLAGS}
"

rm $FILENAME

echo "Done!"
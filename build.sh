#!/bin/bash

PLUGIN="DynamicShop"
PLUGIN_DESTINATION="/home/work/minecraft/latest-paper/plugins"

/opt/gradle/gradle-6.7.1/bin/gradle build
/opt/gradle/gradle-6.7.1/bin/gradle shadowJar

cd build/libs || exit

cp *.jar ${PLUGIN_DESTINATION}/${PLUGIN}.jar

clean_flag='false'

while getopts 'c' flag; do
  case "${flag}" in
    c) clean_flag='true' ;;
  esac
done

if [ "$clean_flag" = "true" ]; then
    echo "Cleaning ${PLUGIN} directory!"
    cd $PLUGIN_DESTINATION || exit
    rm -rf "${PLUGIN:?}"/
fi

